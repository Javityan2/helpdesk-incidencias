import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IncidenciaService, Incidencia } from '../../../services/incidencia.service';
import { DiagramaService } from '../../../services/diagrama.service';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-incidencia-detail',
  templateUrl: './incidencia-detail.component.html',
  styleUrls: ['./incidencia-detail.component.scss']
})
export class IncidenciaDetailComponent implements OnInit, AfterViewInit {
  
  @ViewChild('paretoChart') paretoChartRef!: ElementRef<HTMLCanvasElement>;
  
  incidencia: Incidencia | null = null;
  loading = false;
  error = '';
  activeTab = 'general';

  // Propiedades para diagramas
  diagramaFlujo = '';
  diagramaIshikawa = '';
  lluviaIdeas: string[] = [];
  nuevaIdea = '';
  porques: string[] = [];
  nuevoPorque = '';
  paretoData: any[] = [];
  paretoChart: Chart | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private incidenciaService: IncidenciaService,
    private diagramaService: DiagramaService
  ) {}

  ngOnInit(): void {
    this.cargarIncidencia();
    this.inicializarDiagramas();
    this.diagramaService.inicializarMermaid();
  }

  ngAfterViewInit(): void {
    // Renderizar diagramas después de que la vista esté lista
    setTimeout(() => {
      this.renderizarDiagramas();
    }, 100);
  }

  cargarIncidencia(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.error = 'ID de incidencia no válido';
      return;
    }

    this.loading = true;
    this.error = '';

    this.incidenciaService.getIncidencia(+id)
      .subscribe({
        next: (data) => {
          this.incidencia = data;
          this.loading = false;
          this.generarDiagramaFlujo();
          this.generarDiagramaIshikawa();
          setTimeout(() => {
            this.renderizarDiagramas();
          }, 100);
        },
        error: (error) => {
          console.error('Error al cargar incidencia:', error);
          this.error = 'Error al cargar la incidencia. Inténtalo de nuevo.';
          this.loading = false;
        }
      });
  }

  renderizarDiagramas(): void {
    // Renderizar diagrama de flujo
    if (this.diagramaFlujo) {
      this.diagramaService.renderizarMermaid('diagrama-flujo', this.diagramaFlujo);
    }

    // Renderizar diagrama Ishikawa
    if (this.diagramaIshikawa) {
      this.diagramaService.renderizarMermaid('diagrama-ishikawa', this.diagramaIshikawa);
    }

    // Crear gráfico de Pareto
    if (this.paretoChartRef && this.paretoData.length > 0) {
      this.paretoChart = this.diagramaService.crearGraficoPareto(
        this.paretoChartRef.nativeElement, 
        this.paretoData
      );
    }
  }

  inicializarDiagramas(): void {
    // Inicializar datos de ejemplo
    this.lluviaIdeas = [
      'Falta de capacitación del usuario',
      'Problema de conectividad de red',
      'Configuración incorrecta del sistema',
      'Hardware defectuoso',
      'Software desactualizado'
    ];

    this.porques = [
      '¿Por qué ocurrió el problema?',
      '¿Por qué no se detectó antes?',
      '¿Por qué no hay procedimientos claros?',
      '¿Por qué falta documentación?',
      '¿Por qué no hay respaldo del sistema?'
    ];

    this.paretoData = [
      { causa: 'Falta de capacitación', frecuencia: 15, porcentaje: 30 },
      { causa: 'Problemas de red', frecuencia: 12, porcentaje: 24 },
      { causa: 'Configuración incorrecta', frecuencia: 10, porcentaje: 20 },
      { causa: 'Hardware defectuoso', frecuencia: 8, porcentaje: 16 },
      { causa: 'Software desactualizado', frecuencia: 5, porcentaje: 10 }
    ];
  }

  generarDiagramaFlujo(): void {
    if (!this.incidencia) return;

    this.diagramaFlujo = `
      graph TD
        A[Inicio: ${this.incidencia.titulo}] --> B{¿Problema identificado?}
        B -->|Sí| C[Análisis inicial]
        B -->|No| D[Recopilar información]
        D --> E[Entrevistar usuario]
        E --> C
        C --> F{¿Causa conocida?}
        F -->|Sí| G[Implementar solución]
        F -->|No| H[Análisis Ishikawa]
        H --> I[Identificar causas raíz]
        I --> J[Priorizar causas]
        J --> K[Desarrollar soluciones]
        K --> L[Implementar mejoras]
        L --> M[Verificar resultados]
        M --> N{¿Problema resuelto?}
        N -->|Sí| O[Documentar solución]
        N -->|No| P[Revisar análisis]
        P --> H
        O --> Q[Fin: Incidencia cerrada]
        G --> Q
    `;
  }

  generarDiagramaIshikawa(): void {
    if (!this.incidencia) return;

    this.diagramaIshikawa = `
      graph TD
        subgraph "Problema Principal"
          A[${this.incidencia.titulo}]
        end
        
        subgraph "Causas Principales"
          B[Personas]
          C[Métodos]
          D[Materiales]
          E[Máquinas]
          F[Medio Ambiente]
        end
        
        A --> B
        A --> C
        A --> D
        A --> E
        A --> F
        
        subgraph "Causas Secundarias"
          B1[Falta de capacitación]
          B2[Error humano]
          C1[Procedimientos no claros]
          C2[Documentación insuficiente]
          D1[Equipos defectuosos]
          D2[Materiales inadecuados]
          E1[Equipos desactualizados]
          E2[Mantenimiento deficiente]
          F1[Condiciones ambientales]
          F2[Interferencias externas]
        end
        
        B --> B1
        B --> B2
        C --> C1
        C --> C2
        D --> D1
        D --> D2
        E --> E1
        E --> E2
        F --> F1
        F --> F2
    `;
  }

  agregarIdea(): void {
    if (this.nuevaIdea.trim()) {
      this.lluviaIdeas.push(this.nuevaIdea.trim());
      this.nuevaIdea = '';
    }
  }

  eliminarIdea(index: number): void {
    this.lluviaIdeas.splice(index, 1);
  }

  agregarPorque(): void {
    if (this.nuevoPorque.trim()) {
      this.porques.push(this.nuevoPorque.trim());
      this.nuevoPorque = '';
    }
  }

  eliminarPorque(index: number): void {
    this.porques.splice(index, 1);
  }

  cambiarTab(tab: string): void {
    this.activeTab = tab;
    
    // Renderizar diagramas cuando se cambie a las pestañas correspondientes
    setTimeout(() => {
      if (tab === 'diagrama' && this.diagramaFlujo) {
        this.diagramaService.renderizarMermaid('diagrama-flujo', this.diagramaFlujo);
      } else if (tab === 'ishikawa' && this.diagramaIshikawa) {
        this.diagramaService.renderizarMermaid('diagrama-ishikawa', this.diagramaIshikawa);
      } else if (tab === 'pareto' && this.paretoChartRef && this.paretoData.length > 0) {
        // Destruir gráfico anterior si existe
        if (this.paretoChart) {
          this.paretoChart.destroy();
        }
        this.paretoChart = this.diagramaService.crearGraficoPareto(
          this.paretoChartRef.nativeElement, 
          this.paretoData
        );
      }
    }, 100);
  }

  getPrioridadClass(prioridad: string): string {
    switch (prioridad) {
      case 'ALTA': return 'badge bg-danger';
      case 'MEDIA': return 'badge bg-warning';
      case 'BAJA': return 'badge bg-success';
      default: return 'badge bg-secondary';
    }
  }

  getEstadoClass(estado: string): string {
    switch (estado) {
      case 'PENDIENTE': return 'badge bg-warning';
      case 'EN_PROCESO': return 'badge bg-info';
      case 'RESUELTA': return 'badge bg-success';
      case 'CERRADA': return 'badge bg-secondary';
      default: return 'badge bg-secondary';
    }
  }

  volver(): void {
    this.router.navigate(['/incidencias']);
  }
} 