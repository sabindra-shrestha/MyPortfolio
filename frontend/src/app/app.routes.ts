import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { About } from './pages/about/about';
import { Projects } from './pages/projects/projects';
import { BlogComponent } from './pages/blog/blog';
import { ContactComponent } from './pages/contact/contact';

export const routes: Routes = [
  {path: '', component: Home},
  {path: 'about', component: About},
  {path: 'projects', component: Projects},
  {path: 'blog', component: BlogComponent},
  {path: 'contact', component: ContactComponent},
  {path: '**', redirectTo: ''}
  ];
