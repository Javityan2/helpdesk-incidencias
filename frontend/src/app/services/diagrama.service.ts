import { Injectable } from '@angular/core';
import { Chart, ChartConfiguration, ChartData } from 'chart.js';

@Injectable({
  providedIn: 'root'
})
export class DiagramaService {

  constructor() { }

  /**
   * Renderiza un diagrama Mermaid
   */
  renderizarMermaid(elementId: string, diagrama: string): void {
    const element = document.getElementById(elementId);
    if (!element) {
      console.error(`Elemento con ID ${elementId} no encontrado`);
      return;
    }

    // Limpiar contenido anterior
    element.innerHTML = '';

    // Crear nuevo elemento para el diagrama
    const mermaidElement = document.createElement('div');
    mermaidElement.className = 'mermaid';
    mermaidElement.textContent = diagrama;
    element.appendChild(mermaidElement);

    // Renderizar con Mermaid
    try {
      // @ts-ignore - Mermaid está disponible globalmente
      if (typeof mermaid !== 'undefined') {
        // @ts-ignore
        mermaid.render(`${elementId}-svg`, diagrama).then((result: any) => {
          element.innerHTML = result.svg;
        }).catch((error: any) => {
          console.error('Error al renderizar diagrama Mermaid:', error);
          element.innerHTML = '<div class="alert alert-warning">Error al renderizar el diagrama</div>';
        });
      } else {
        console.warn('Mermaid no está disponible');
        element.innerHTML = '<div class="alert alert-info">Cargando diagrama...</div>';
      }
    } catch (error) {
      console.error('Error al inicializar Mermaid:', error);
      element.innerHTML = '<div class="alert alert-danger">Error al cargar el diagrama</div>';
    }
  }

  /**
   * Crea un gráfico de Pareto usando Chart.js
   */
  crearGraficoPareto(canvas: HTMLCanvasElement, data: any[]): Chart | null {
    if (!canvas) {
      console.error('Canvas no encontrado');
      return null;
    }

    const ctx = canvas.getContext('2d');
    if (!ctx) {
      console.error('No se pudo obtener el contexto del canvas');
      return null;
    }

    // Preparar datos para el gráfico
    const labels = data.map(item => item.causa);
    const frecuencias = data.map(item => item.frecuencia);
    const porcentajes = data.map(item => item.porcentaje);

    // Calcular porcentaje acumulado
    let acumulado = 0;
    const porcentajeAcumulado = porcentajes.map(porcentaje => {
      acumulado += porcentaje;
      return acumulado;
    });

    const chartData: ChartData<'bar' | 'line'> = {
      labels: labels,
      datasets: [
        {
          label: 'Frecuencia',
          data: frecuencias,
          backgroundColor: 'rgba(54, 162, 235, 0.8)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1,
          yAxisID: 'y'
        },
        {
          label: 'Porcentaje Acumulado',
          data: porcentajeAcumulado,
          type: 'line',
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 2,
          fill: false,
          yAxisID: 'y1'
        }
      ]
    };

    const config: ChartConfiguration<'bar' | 'line'> = {
      type: 'bar',
      data: chartData,
      options: {
        responsive: true,
        maintainAspectRatio: false,
        interaction: {
          mode: 'index' as const,
          intersect: false,
        },
        plugins: {
          title: {
            display: true,
            text: 'Análisis de Pareto - Principio 80/20'
          },
          tooltip: {
            callbacks: {
              label: function(context) {
                const label = context.dataset.label || '';
                const value = context.parsed.y;
                if (context.datasetIndex === 0) {
                  return `${label}: ${value} ocurrencias`;
                } else {
                  return `${label}: ${value.toFixed(1)}%`;
                }
              }
            }
          }
        },
        scales: {
          x: {
            display: true,
            title: {
              display: true,
              text: 'Causas'
            }
          },
          y: {
            type: 'linear' as const,
            display: true,
            position: 'left' as const,
            title: {
              display: true,
              text: 'Frecuencia'
            }
          },
          y1: {
            type: 'linear' as const,
            display: true,
            position: 'right' as const,
            title: {
              display: true,
              text: 'Porcentaje Acumulado (%)'
            },
            grid: {
              drawOnChartArea: false,
            },
            max: 100
          }
        }
      }
    };

    try {
      return new Chart(ctx, config);
    } catch (error) {
      console.error('Error al crear el gráfico:', error);
      return null;
    }
  }

  /**
   * Inicializa Mermaid globalmente
   */
  inicializarMermaid(): void {
    try {
      // @ts-ignore
      if (typeof mermaid !== 'undefined') {
        // @ts-ignore
        mermaid.initialize({
          startOnLoad: false,
          theme: 'default',
          flowchart: {
            useMaxWidth: true,
            htmlLabels: true,
            curve: 'basis'
          }
        });
      }
    } catch (error) {
      console.error('Error al inicializar Mermaid:', error);
    }
  }
} 