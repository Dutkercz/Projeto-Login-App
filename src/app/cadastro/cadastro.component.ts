import { Component, inject, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { CommonModule } from '@angular/common';
import {ReactiveFormsModule, FormGroup, Validators, FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.scss'
})
export class CadastroComponent {

  camposForm: FormGroup
  usuarioCampo: Usuario[] = []
  private http = inject(HttpClient)

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
      this.http.post('http://localhost:8080/usuarios', newUser)
        .subscribe({
          next: (x) => {
            console.log('Usuário cadastrado com sucesso:', x);
            this.camposForm.reset();
          },
          error: (x) => {
            console.error('Erro ao cadastrar usuário:', x);
          }
        })
      this.camposForm.reset()
    }
  }

    isCampoInvalido(nomeDoCampo: string, erro: string = 'required'): boolean{
    const campo = this.camposForm.get(nomeDoCampo)
    return !!(campo && campo.invalid && campo.touched && campo.errors?.[erro])
  }
}
