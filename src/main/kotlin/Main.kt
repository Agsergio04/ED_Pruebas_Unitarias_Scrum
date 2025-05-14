package org.example

import DashboardService
import org.example.aplicacion.ActividadService
import org.example.aplicacion.HistorialService
import org.example.datos.ActividadRepository
import org.example.datos.HistorialRepository
import org.example.datos.UsuarioRepository
import org.example.presentacion.ConsolaUI

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val actividadRepo = ActividadRepository()
    val usuarioRepo = UsuarioRepository()
    val historialRepo = HistorialRepository()
    val historialService = HistorialService(historialRepo)
    val dashboardService = DashboardService(actividadRepo)
    val servicio = ActividadService(
        actividadRepo = actividadRepo,
        usuarioRepo = usuarioRepo,
        historialService = historialService,
        dashboardService = dashboardService)

    ConsolaUI(servicio,dashboardService).iniciar()
}