import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ContactService } from '../../services/contact';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contact.html',
  styleUrl: './contact.scss',
})
export class ContactComponent {
  contactForm: FormGroup;
  submitted = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private contactService: ContactService
    ) {
      this.contactForm = this.fb.group({
        name: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        subject: [''],
        message: ['', Validators.required]
        });
      }

    onSubmit(): void{
      if(this.contactForm.invalid){
        return;
        }
      this.contactService.submitContact(this.contactForm.value).subscribe({
        next: () => {
          this.submitted = true;
          this.contactForm.reset();
          },
        error: (err) => {
          console.error('Failed to submit contact form', err);
          this.errorMessage = 'Something went wrong. Please try again.';
          }
        });
      }
  }
