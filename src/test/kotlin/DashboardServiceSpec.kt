// src/test/kotlin/org/example/aplicacion/DashboardServiceSpec.kt
package org.example.aplicacion

import DashboardService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.example.datos.IActividadRepository
import org.example.dominio.EstadoTarea
import java.time.LocalDate

class DashboardServiceSpec : DescribeSpec({
    val actividadRepo = mockk<IActividadRepository>()
    val dashboardService = DashboardService(actividadRepo)

    beforeTest { clearAllMocks() }

    describe("obtenerMetricasHoy") {
        context("con datos del repositorio") {
            it("debe retornar métricas correctas") {
                // Configurar mocks
                every { actividadRepo.contarTareasPorEstado(EstadoTarea.ABIERTA) } returns 3
                every { actividadRepo.contarTareasPorEstado(EstadoTarea.EN_PROGRESO) } returns 2
                every { actividadRepo.contarTareasPorEstado(EstadoTarea.ACABADA) } returns 5
                every { actividadRepo.obtenerEventosEntreFechas(any(), any()) } returns listOf("Evento1", "Evento2")
                every { actividadRepo.contarTareasConSubtareas() } returns 4

                // Ejecutar
                val metricas = dashboardService.obtenerMetricasHoy()

                // Verificar
                metricas["tareasAbiertas"] shouldBe 3
                metricas["tareasEnProgreso"] shouldBe 2
                metricas["eventosHoy"] shouldBe listOf("Evento1", "Evento2")
            }
        }
    }

    describe("obtenerMetricasSemana") {
        context("al solicitar eventos de la semana") {
            it("debe usar fechas correctas") {
                // Configurar mock
                val hoy = LocalDate.now()
                val finSemana = hoy.plusDays(7).toString()
                every { actividadRepo.obtenerEventosEntreFechas(any(), any()) } returns emptyList()

                // Ejecutar
                dashboardService.obtenerMetricasSemana()

                // Verificar llamadas
                verify {
                    actividadRepo.obtenerEventosEntreFechas(hoy.toString(), finSemana)
                }
            }
        }
    }
})