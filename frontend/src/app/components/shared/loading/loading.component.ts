import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss']
})
export class LoadingComponent {
  @Input() message: string = 'Cargando...';
  @Input() size: 'small' | 'medium' | 'large' = 'medium';

  getSpinnerSize(): number {
    switch (this.size) {
      case 'small':
        return 20;
      case 'large':
        return 60;
      default:
        return 40;
    }
  }
} 