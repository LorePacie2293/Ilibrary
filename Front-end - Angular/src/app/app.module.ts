import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {MatRadioModule} from '@angular/material/radio';
import { DialogLoginComponent } from './Dialog/dialog-login/dialog-login.component';
import {MatMenuModule} from '@angular/material/menu';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DialogLogoutComponent } from './Dialog/dialog-logout/dialog-logout.component';
import { GridBooksPageComponent } from './pages/grid-books-page/grid-books-page.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule} from '@angular/material/card';
import {MatSliderModule} from '@angular/material/slider';
import { ToolbarComponent } from './toolbars/toolbar/toolbar.component';
import { CardArticoloComponent } from './card-articolo/card-articolo.component';
import { OrdiniComponent } from './pages/ordini/ordini.component';
import {MatTableModule} from '@angular/material/table';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BooksPageComponent } from './pages/books-page/books-page.component';
import { NgxPaginationModule } from 'ngx-pagination';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { DialogUpdateComponent } from './Dialog/dialog-update/dialog-update.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AuthInterceptorService } from './Service/Interceptor/auth-interceptor.service';
import {MatBadgeModule} from '@angular/material/badge';
import { DialogInsertComponent } from './Dialog/dialog-insert/dialog-insert.component';
import { DialogRegisterComponent } from './Dialog/dialog-register/dialog-register.component';
import { OrdiniUsernameComponent } from './pages/ordini-username/ordini-username.component';
import { DialogOrderDetailsComponent } from './Dialog/dialog-order-details/dialog-order-details.component';
import { UsersPageComponent } from './pages/users-page/users-page.component';
import { DialogListOrderUsernameComponent } from './Dialog/dialog-list-order-username/dialog-list-order-username.component';
import { CartUsernamePageComponent } from './pages/cart-username-page/cart-username-page.component';
import { DialogMsgComponent } from './Dialog/dialog-msg/dialog-msg.component';
import { DialogCartBooksByUsernameComponent } from './Dialog/dialog-cart-books-by-username/dialog-cart-books-by-username.component';


@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    DialogInsertComponent,
    DialogLoginComponent,
    DialogLogoutComponent,
    GridBooksPageComponent,
    CardArticoloComponent,
    OrdiniComponent,
    BooksPageComponent,
    DialogUpdateComponent,
    DialogRegisterComponent,
    OrdiniUsernameComponent,
    DialogOrderDetailsComponent,
    UsersPageComponent,
    DialogListOrderUsernameComponent,
    CartUsernamePageComponent,
    DialogMsgComponent,
    DialogCartBooksByUsernameComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    MatMenuModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatCardModule,
    MatSliderModule,
    MatTableModule,
    HttpClientModule,
    NgxPaginationModule,
    MatSnackBarModule,
    NgMultiSelectDropDownModule.forRoot(),
    MatBadgeModule

  ],
  providers: [

    //Attivazione INTERCETTORE
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
