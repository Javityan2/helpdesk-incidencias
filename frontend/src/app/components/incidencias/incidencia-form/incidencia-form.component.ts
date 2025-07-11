import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IncidenciasService, Incidencia } from '../../../services/incidencias.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { trigger, state, style, transition, animate, keyframes } from '@angular/animations';

@Component({
  selector: 'app-incidencia-form',
  templateUrl: './incidencia-form.component.html',
  styleUrls: ['./incidencia-form.component.scss'],
  animations: [
    trigger('fadeInUp', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(20px)' }),
        animate('400ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ]),
    trigger('slideInRight', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateX(20px)' }),
        animate('300ms ease-out', style({ opacity: 1, transform: 'translateX(0)' }))
      ])
    ]),
    trigger('scaleIn', [
      transition(':enter', [
        style({ opacity: 0, transform: 'scale(0.9)' }),
        animate('300ms ease-out', style({ opacity: 1, transform: 'scale(1)' }))
      ])
    ]),
    trigger('formField', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(10px)' }),
        animate('200ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ])
  ]
})
export class IncidenciaFormComponent implements OnInit {
  incidenciaForm: FormGroup;
  loading = false;
  isEditMode = false;
  incidenciaId?: number;

  estados = [
    { value: 'ABIERTA', label: 'Abierta' },
    { value: 'EN_PROCESO', label: 'En Proceso' },
    { value: 'RESUELTA', label: 'Resuelta' },
    { value: 'CERRADA', label: 'Cerrada' }
  ];

  prioridades = [
    { value: 'ALTA', label: 'Alta' },
    { value: 'MEDIA', label: 'Media' },
    { value: 'BAJA', label: 'Baja' }
  ];

  categorias = [
    { value: 'SOFTWARE', label: 'Software' },
    { value: 'HARDWARE', label: 'Hardware' },
    { value: 'RED', label: 'Red' },
    { value: 'SEGURIDAD', label: 'Seguridad' },
    { value: 'ACCESO', label: 'Acceso' },
    { value: 'IMPRESION', label: 'Impresión' },
    { value: 'EMAIL', label: 'Email' },
    { value: 'BASE_DATOS', label: 'Base de Datos' },
    { value: 'SERVIDOR', label: 'Servidor' },
    { value: 'APLICACION', label: 'Aplicación' },
    { value: 'CONFIGURACION', label: 'Configuración' },
    { value: 'MANTENIMIENTO', label: 'Mantenimiento' },
    { value: 'CAPACITACION', label: 'Capacitación' },
    { value: 'OTROS', label: 'Otros' }
  ];

  constructor(
    private fb: FormBuilder,
    private incidenciasService: IncidenciasService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    this.incidenciaForm = this.fb.group({
      titulo: ['', [Validators.required, Validators.minLength(5)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      estado: ['ABIERTA', Validators.required],
      prioridad: ['MEDIA', Validators.required],
      categoria: ['SOFTWARE', Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditMode = true;
        this.incidenciaId = +params['id'];
        this.loadIncidencia();
      }
    });
  }

  loadIncidencia(): void {
    if (!this.incidenciaId) return;

    this.loading = true;
    this.incidenciasService.getIncidencia(this.incidenciaId).subscribe({
      next: (response) => {
        if (response.success) {
          const incidencia = response.data as Incidencia;
          this.incidenciaForm.patchValue({
            titulo: incidencia.titulo,
            descripcion: incidencia.descripcion,
            estado: incidencia.estado,
            prioridad: incidencia.prioridad,
            categoria: incidencia.categoria
          });
        } else {
          this.showErrorSnackbar(response.message || 'Error al cargar incidencia');
          this.router.navigate(['/incidencias']);
        }
      },
      error: (error) => {
        console.error('Error cargando incidencia:', error);
        this.showErrorSnackbar('Error al cargar la incidencia');
        this.router.navigate(['/incidencias']);
      },
      complete: () => {
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.incidenciaForm.valid) {
      this.loading = true;
      const incidenciaData: Incidencia = this.incidenciaForm.value;

      if (this.isEditMode && this.incidenciaId) {
        this.incidenciasService.updateIncidencia(this.incidenciaId, incidenciaData).subscribe({
          next: (response) => {
            if (response.success) {
              this.showSuccessSnackbar('Incidencia actualizada correctamente');
              this.router.navigate(['/incidencias', this.incidenciaId]);
            } else {
              this.showErrorSnackbar(response.message || 'Error al actualizar incidencia');
            }
          },
          error: (error) => {
            console.error('Error actualizando incidencia:', error);
            this.showErrorSnackbar('Error al actualizar la incidencia');
          },
          complete: () => {
            this.loading = false;
          }
        });
      } else {
        this.incidenciasService.createIncidencia(incidenciaData).subscribe({
          next: (response) => {
            if (response.success) {
              this.showSuccessSnackbar('Incidencia creada correctamente');
              this.router.navigate(['/incidencias']);
            } else {
              this.showErrorSnackbar(response.message || 'Error al crear incidencia');
            }
          },
          error: (error) => {
            console.error('Error creando incidencia:', error);
            this.showErrorSnackbar('Error al crear la incidencia');
          },
          complete: () => {
            this.loading = false;
          }
        });
      }
    } else {
      this.markFormGroupTouched();
    }
  }

  onCancel(): void {
    if (this.isEditMode && this.incidenciaId) {
      this.router.navigate(['/incidencias', this.incidenciaId]);
    } else {
      this.router.navigate(['/incidencias']);
    }
  }

  markFormGroupTouched(): void {
    Object.keys(this.incidenciaForm.controls).forEach(key => {
      const control = this.incidenciaForm.get(key);
      control?.markAsTouched();
    });
  }

  getErrorMessage(controlName: string): string {
    const control = this.incidenciaForm.get(controlName);
    if (control?.hasError('required')) {
      return 'Este campo es requerido';
    }
    if (control?.hasError('minlength')) {
      const requiredLength = control.getError('minlength').requiredLength;
      return `Mínimo ${requiredLength} caracteres`;
    }
    return '';
  }

  private showSuccessSnackbar(message: string): void {
    this.snackBar.open(message, 'Cerrar', {
      duration: 3000,
      panelClass: ['success-snackbar']
    });
  }

  private showErrorSnackbar(message: string): void {
    this.snackBar.open(message, 'Cerrar', {
      duration: 5000,
      panelClass: ['error-snackbar']
    });
  }
} 