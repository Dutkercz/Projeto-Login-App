import { Component, inject, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { CommonModule } from '@angular/common';
import {ReactiveFormsModule, FormGroup, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  camposForm: FormGroup
  usuarioCampo: Usuario[] = []
  
  constructor(){
    this.camposForm = new FormGroup({
      name: new FormControl('', Validators.required),
      email: new FormControl('', Validators.email),
      password: new FormControl('', Validators.required)
    })

  }

  salvar(){
    this.camposForm.markAllAsTouched()  
    if(this.camposForm.valid){
      const newUser : Usuario = {
        name: this.camposForm.value.name,
        email: this.camposForm.value.email,
        password: this.camposForm.value.password
      }
      this.usuarioCampo.push(newUser)
      console.log("Usuário cadastrdo ", newUser)
      this.camposForm.reset()
    }
  }

    isCampoInvalido(nomeDoCampo: string, erro: string = 'required'): boolean{
    const campo = this.camposForm.get(nomeDoCampo)
    return !!(campo && campo.invalid && campo.touched && campo.errors?.[erro])
  }
}
