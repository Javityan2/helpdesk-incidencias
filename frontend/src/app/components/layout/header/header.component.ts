import { Component, Input, Output, EventEmitter } from '@angular/core';
import { User } from '../../../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  @Input() currentUser: User | null = null;
  @Output() logout = new EventEmitter<void>();

  onLogout(): void {
    this.logout.emit();
  }

  getUserDisplayName(): string {
    if (!this.currentUser) return '';
    return `${this.currentUser.nombre} ${this.currentUser.apellido}`;
  }
} 