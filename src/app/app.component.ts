import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CadastroComponent } from "./cadastro/cadastro.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CadastroComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'login-app';
}
