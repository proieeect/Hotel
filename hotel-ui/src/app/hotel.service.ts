import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {map, Observable, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface UserPositionRequest {
  latitude: number;
  longitude: number;
  radius: number;
}

export interface HotelResponseDto {
  id: number;
  hotelName: string;
}

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  private apiUrl = 'http://localhost:8080/hotels/position'; // Replace with your backend URL
  private typesRoomUrl = 'http://localhost:8080/hotels/type-rooms'; // Replace with your backend URL
  private cancelBookingUrl = 'http://localhost:8080/hotels/delete'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  getHotelsByUserPosition(request: UserPositionRequest): Observable<HotelResponseDto[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<HotelResponseDto[]>(this.apiUrl, request, { headers })
      .pipe(
        catchError(error => {
          console.error('Error fetching hotels', error);
          throw error;
        })
      );
  }

  getRoomsTypeForHotel(hotelId: number): Observable<String[]> {
    return this.http.get<String[]>(`${this.typesRoomUrl}/${hotelId}`)
      .pipe(
        catchError(error => {
          console.error('Error fetching room types', error);
          return throwError(error);
        })
      );
  }

  cancelBooking(bookingId: Number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.cancelBookingUrl}/${bookingId}`)
      .pipe(
        catchError(error => {
          console.error('Error fetching room types', error);
          return throwError(error);
        })
      );
  }


}
