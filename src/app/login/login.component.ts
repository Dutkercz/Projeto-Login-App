import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Usuariologin } from '../usuariologin';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule, CommonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm: FormGroup
  private http = inject(HttpClient)
  private router = inject(Router)
  loginErro : boolean = false

  constructor(){
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    })
  }

  login(){

    this.loginForm.markAllAsTouched()
    if(this.loginForm.valid){
      const userLogin : Usuariologin = {
        username: this.loginForm.value.username,
        password: this.loginForm.value.password
      }
      this.http.post('http://localhost:8080/auth', userLogin, {withCredentials: true}).subscribe({
        next: x => {this.loginErro = false, console.log(x), this.router.navigate(['/home'])},
        error: x => { this.loginErro = true, console.log(x)}
      })
    }
  }

   isCampoInvalido(nomeDoCampo: string, erro: string = 'required'): boolean{
    const campo = this.loginForm.get(nomeDoCampo)
    return !!(campo && campo.invalid && campo.touched && campo.errors?.[erro])
  }
}
