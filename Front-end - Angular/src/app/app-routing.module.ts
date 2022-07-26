import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Roles } from './model/roles';
import { BooksPageComponent } from './pages/books-page/books-page.component';
import { CartUsernamePageComponent } from './pages/cart-username-page/cart-username-page.component';
import { GridBooksPageComponent } from './pages/grid-books-page/grid-books-page.component';
import { OrdiniUsernameComponent } from './pages/ordini-username/ordini-username.component';
import { OrdiniComponent } from './pages/ordini/ordini.component';
import { UsersPageComponent } from './pages/users-page/users-page.component';
import { RouteProtectService } from './Service/route-protect.service';

const routes: Routes = [
  {path: "", component: GridBooksPageComponent},
  {path: "book/listGrid", component: GridBooksPageComponent},
  {path: "book/listTable", component: BooksPageComponent},
  {path: "ordini/all", component: OrdiniComponent, canActivate: [RouteProtectService], data:{roles: [Roles.admin]}},
  {path: "users/all", component: UsersPageComponent, canActivate: [RouteProtectService], data:{roles: [Roles.admin]}},
  {path: "ordini/username", component: OrdiniUsernameComponent, canActivate: [RouteProtectService], data:{roles: [Roles.user, Roles.admin]}},
  {path: "cart/username", component: CartUsernamePageComponent, canActivate: [RouteProtectService], data:{roles: [Roles.user, Roles.admin]}}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {


}
