import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IncidenciasService, Incidencia } from '../../../services/incidencias.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-incidencia-form',
  templateUrl: './incidencia-form.component.html',
  styleUrls: ['./incidencia-form.component.scss']
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
    { value: 'USUARIO', label: 'Usuario' },
    { value: 'OTRO', label: 'Otro' }
  ];

  constructor(
    private fb: FormBuilder,
    private incidenciasService: IncidenciasService,
    private router: Router,
    private route: ActivatedRoute,
    private toastr: ToastrService
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
          this.toastr.error(response.message, 'Error');
          this.router.navigate(['/incidencias']);
        }
      },
      error: (error) => {
        console.error('Error cargando incidencia:', error);
        this.toastr.error('Error al cargar la incidencia', 'Error');
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
              this.toastr.success('Incidencia actualizada correctamente', 'Éxito');
              this.router.navigate(['/incidencias', this.incidenciaId]);
            } else {
              this.toastr.error(response.message, 'Error');
            }
          },
          error: (error) => {
            console.error('Error actualizando incidencia:', error);
            this.toastr.error('Error al actualizar la incidencia', 'Error');
          },
          complete: () => {
            this.loading = false;
          }
        });
      } else {
        this.incidenciasService.createIncidencia(incidenciaData).subscribe({
          next: (response) => {
            if (response.success) {
              this.toastr.success('Incidencia creada correctamente', 'Éxito');
              this.router.navigate(['/incidencias']);
            } else {
              this.toastr.error(response.message, 'Error');
            }
          },
          error: (error) => {
            console.error('Error creando incidencia:', error);
            this.toastr.error('Error al crear la incidencia', 'Error');
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
} 