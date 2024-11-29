import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  // Save JWT token to localStorage
  saveToken(token: string): void {
    localStorage.setItem('auth_token', token);
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post<any>(this.apiUrl + "/login", { email, password }).pipe(
      tap(response => {
        if (response.token) {
          this.saveToken(response.token); // Save token after successful login
          this.setUserDetails(response.user)
        }
      })
    );
  }

  register(email: string, password: string, phone: string, name: string,gender: boolean): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, { email, password ,phone,name, gender});
  }

  // Optionally, you can store the user details in local storage after successful login
  setUserDetails(user: any): void {
    localStorage.setItem('user', JSON.stringify(user)); // Save user to localStorage
  }

  getUserDetails(): any {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null; // Retrieve user from localStorage
  }

  logout(): void {
    this.removeToken(); // Remove user from localStorage
    localStorage.removeItem('user'); // Remove user from localStorage
  }

  // Get JWT token from localStorage
  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  // Remove JWT token (when logging out)
  removeToken(): void {
    localStorage.removeItem('auth_token');
  }
}
