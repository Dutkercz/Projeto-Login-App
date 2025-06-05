import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Usuariologin } from '../usuariologin';
import { CommonModule } from '@angular/common';

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
  loginErro : boolean = false

  constructor(){
    this.loginForm = new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    })
  }

  login(){

    this.loginForm.markAllAsTouched()
    if(this.loginForm.valid){
      const userLogin : Usuariologin = {
        email: this.loginForm.value.email,
        password: this.loginForm.value.password
      }
      this.http.post('http://localhost:8080/auth', userLogin, {withCredentials: true}).subscribe({
        next: x => {this.loginErro = false},
        error: x => { this.loginErro = true}
      })
    }
  }

   isCampoInvalido(nomeDoCampo: string, erro: string = 'required'): boolean{
    const campo = this.loginForm.get(nomeDoCampo)
    return !!(campo && campo.invalid && campo.touched && campo.errors?.[erro])
  }
}
