import { Component, inject, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { CommonModule } from '@angular/common';
import {ReactiveFormsModule, FormGroup, Validators, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.scss'
})
export class CadastroComponent {

  camposForm: FormGroup
  private http = inject(HttpClient)
  private router = inject(Router)

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
      this.http.post('https://back-loginapp.onrender.com/usuarios', newUser)
        .subscribe({
          next: (x) => {this.router.navigate(['/auth']), this.camposForm.reset()},
          error: (x) => {console.error('Erro ao cadastrar usuário:', x)}
        })
      this.camposForm.reset()
    }
  }

  isCampoInvalido(nomeDoCampo: string, erro: string = 'required'): boolean{
    const campo = this.camposForm.get(nomeDoCampo)
    return !!(campo && campo.invalid && campo.touched && campo.errors?.[erro])
  }
}
