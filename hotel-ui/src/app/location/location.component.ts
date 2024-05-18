import { Component, OnInit } from '@angular/core';
import { HotelService, HotelResponseDto, UserPositionRequest } from '../hotel.service';
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {
  userLatitude: number | null = null;
  userLongitude: number | null = null;
  hotels: HotelResponseDto[] = [];
  selectedHotelId: number | null = null;
  selectedHotel: HotelResponseDto | null = null;
  roomTypes: String[] = [];
  selectedRadius: number | null = null; // Store the selected radius as a number
  availableRadii: number[] = [1, 5, 10]; // Define available radius options
  bookingId:Number;

  constructor(private hotelService: HotelService,  private sanitizer: DomSanitizer) {}

  ngOnInit() {
    this.getUserLocation();
  }

  getUserLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(position => {
        this.userLatitude = position.coords.latitude;
        this.userLongitude = position.coords.longitude;
        this.fetchHotels();
      }, error => {
        console.error('Error getting user location', error);
      });
    } else {
      console.error('Geolocation is not supported by this browser.');
    }
  }

  fetchHotels() {
    if (this.userLatitude !== null && this.userLongitude !== null && this.selectedRadius) {
      const request: UserPositionRequest = {
        latitude: this.userLatitude,
        longitude: this.userLongitude,
        radius: this.selectedRadius
      };

      this.hotelService.getHotelsByUserPosition(request).subscribe(
        data => {
          this.hotels = data;
        },
        error => {
          console.error('Error fetching hotels', error);
        }
      );
    } else {
      this.hotels = []; // Clear hotels array if no radius selected
    }
  }

  onHotelChange(event: any) {
    this.selectedHotelId = event.target.value;
    this.selectedHotel = null; // Reset selected hotel and room types
    this.roomTypes = [];

    if (this.selectedHotelId) {
      this.hotelService.getRoomsTypeForHotel(this.selectedHotelId).subscribe(
        data => {
          this.roomTypes = data;
        },
        error => {
          console.error('Error fetching room types', error);
        }
      );
    }
  }

  cancelBooking(bookingId: Number) {
    this.hotelService.cancelBooking(bookingId).subscribe(
      isDeleted => {
        if (isDeleted) {
          this.openConfirmationWindow(`Booking canceled successfully!`);
        } else {
          console.error('Error canceling booking.');
          // Handle cancellation failure (optional: display error message)
        }
      },
      error => {
        this.openConfirmationWindow(`Error canceling booking`);
        console.error('Error canceling booking:', error);
        // Handle error gracefully (optional: display error message)
      }
    );
  }

  openConfirmationWindow(message: string) {
    const win = window.open('', '_blank'); // Open a new window
    win.document.write(`<p>${message}</p>`); // Set the content of the window
    win.document.close(); // Close the window after writing content
  }

  getSelectedHotel(): HotelResponseDto | null {
    if (this.selectedHotelId && this.hotels) {
      return this.hotels.find(hotel => hotel.id === this.selectedHotelId);
    }
    return null;
  }
}
