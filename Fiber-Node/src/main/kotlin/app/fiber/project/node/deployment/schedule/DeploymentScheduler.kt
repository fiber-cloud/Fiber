package app.fiber.project.node.deployment.schedule

import app.fiber.project.node.deployment.JarDeployUnit
import app.fiber.project.node.deployment.impl.process.ProcessDeploymentProvider
import app.fiber.project.node.deployment.lifecycle.LifeCycle
import app.fiber.project.node.deployment.strategy.impl.DefaultDeployStrategy
import java.util.*
import kotlin.concurrent.schedule

class DeploymentScheduler {

    private val timer = Timer("deployment scheduler")

    private val deploymentProvider = ProcessDeploymentProvider()

    private val unitQueue: PriorityQueue<JarDeployUnit> = PriorityQueue()

    private var deployStrategy = DefaultDeployStrategy()

    private var currentJarDeployUnit: JarDeployUnit? = null

    fun start() = this.timer.schedule(0, 1000) {
        this@DeploymentScheduler.update()
    }

    private fun update() {
        val jarDeployUnitList = this.deployStrategy.collectDeployUnits()

        jarDeployUnitList.forEach { this.unitQueue.offer(it) }

        if (this.unitQueue.isEmpty()) return

        if (this.currentJarDeployUnit == null ||
            (this.currentJarDeployUnit!!.lifeCycle != LifeCycle.OFFLINE && this.currentJarDeployUnit!!.lifeCycle != LifeCycle.STARTING)
        ) {
            this.currentJarDeployUnit = this.unitQueue.poll()
        } else {
            return
        }

        this.deploymentProvider.deploy(this.currentJarDeployUnit!!)
    }

    fun stop() = this.timer.cancel()

}