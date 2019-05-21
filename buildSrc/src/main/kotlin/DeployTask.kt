import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.LocalFileContent
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files
import java.nio.file.Paths

open class DeployTask : DefaultTask() {

    private var uploadUrl: String = ""

    private val token = System.getenv("FIBER_TOKEN") ?: throw GradleException("Token not available")

    @TaskAction
    fun deploy() {
        val client = HttpClient(Apache) {
            install(JsonFeature)
        }

        this.createRelease(client)
        this.uploadJar(client)
    }

    private fun createRelease(client: HttpClient) {
        val version = this.project.version.toString()

        runBlocking {
            println("Create release..")

            val response = client.post<HttpResponse> {
                url("https://api.github.com/repos/fiber-cloud/Fiber/releases")
                contentType(ContentType.Application.Json)
                body = Release(
                    tagName = version,
                    name = "Fiber $version",
                    body = this@DeployTask.loadChangelog()
                )
                header("Authorization", "token ${this@DeployTask.token}")
            }

            if (response.status != HttpStatusCode.Created) {
                throw GradleException("Creating a release failed!")
            }

            println("Release created")

            this@DeployTask.readAndSetUploadUrl(response)
        }
    }

    private fun readAndSetUploadUrl(response: HttpResponse) = runBlocking {
        launch(Dispatchers.IO) {
            val jsonContent = response.readText()
            this@DeployTask.uploadUrl =
                jacksonObjectMapper().readValue(jsonContent, ObjectNode::class.java).get("upload_url").textValue()
                    ?: throw GradleException("Upload url not found!")
        }
    }

    private fun uploadJar(client: HttpClient) {
        if (this.uploadUrl.isEmpty()) throw GradleException("Upload url is empty!")

        val jarAsset = this.getRootFile("Fiber-Node/build/libs/Fiber-Node-${this.project.version}.jar")
        val url = this.uploadUrl.removeSuffix("{?name,label}").plus("?name=Fiber-Node.jar")

        runBlocking {
            println("Upload jar file..")

            val response = client.post<HttpResponse>() {
                url(url)
                body = LocalFileContent(jarAsset.toFile())
                header("Authorization", "token ${this@DeployTask.token}")
            }

            if (response.status != HttpStatusCode.Created) {
                throw GradleException("Could not upload release assets!")
            }

            println("Jar file uploaded")
        }
    }

    private fun loadChangelog(): String {
        val changelog = this.getRootFile(".changelog/${this.project.version}.md")
        if (Files.notExists(changelog)) throw GradleException("No changelog created!")

        return changelog.toFile().readText()
    }

    private fun getRootFile(path: String) = Paths.get(this.project.rootProject.rootDir.absolutePath, path)

}

data class Release(
    @JsonProperty("tag_name") val tagName: String,
    @JsonProperty("target_commitsh") val targetCommitSh: String = "master",
    val name: String,
    val body: String,
    val draft: Boolean = false,
    @JsonProperty("prerelease") val preRelease: Boolean = false
)