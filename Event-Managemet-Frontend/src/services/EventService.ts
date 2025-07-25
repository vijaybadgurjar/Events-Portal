import axios, { AxiosInstance, AxiosResponse } from 'axios';

const API_URL = 'http://localhost:8080/api/events';

class EventService {
    private axiosInstance: AxiosInstance;

    constructor() {
        this.axiosInstance = axios.create({
            baseURL: API_URL,
            headers: {
                'Content-Type': 'application/json',
            },
            timeout: 10000, // 10 seconds
        });

        // Add a request interceptor to include the JWT token in the Authorization header
        this.axiosInstance.interceptors.request.use(
            (config) => {
                const token = localStorage.getItem('jwtToken');
                console.log('Retrieved Token:', token); // Debugging: Log the token
                if (token) {
                    config.headers['Authorization'] = `Bearer ${token}`;
                    console.log('Authorization Header Set:', config.headers['Authorization']); // Debugging: Log the header
                }
                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );

        this.axiosInstance.interceptors.response.use(
            this.handleResponse,
            this.handleError
        );
    }

    private handleResponse(response: AxiosResponse) {
        return response;
    }

    private handleError(error: any) {
        if (error.response) {
            // Server responded with a status other than 200 range
            console.error('Server Error:', error.response.data);
            return Promise.reject(error.response.data);
        } else if (error.request) {
            // Request was made but no response received
            console.error('Network Error:', error.request);
            return Promise.reject({ message: 'Network Error' });
        } else {
            // Something else happened
            console.error('Error:', error.message);
            return Promise.reject({ message: error.message });
        }
    }

    async myEvents(): Promise<Event[]> {
        try {
            const response = await this.axiosInstance.get('/organizer');
            return response.data;
        } catch (error) {
            return Promise.reject(error);
        }
    }

    async getEvents(): Promise<Event[]> {
        try {
            const response = await this.axiosInstance.get('/all');
            return response.data;
        } catch (error) {
            return Promise.reject(error);
        }
    }

    async getEventById(id: number): Promise<Event> {
        try {
            const response = await this.axiosInstance.get(`/${id}`);
            return response.data;
        } catch (error) {
            return Promise.reject(error);
        }
    }

    async createEvent(event: EventCreateRequest): Promise<void> {
        console.log("creat eevent service");

        try {
            await this.axiosInstance.post('/', event);
        } catch (error) {
            return Promise.reject(error);
        }
    }

    async updateEvent(event: Event): Promise<void> {
        try {
            await this.axiosInstance.put(`/${event.id}`, event);
        } catch (error) {
            return Promise.reject(error);
        }
    }

    async deleteEvent(id: number): Promise<void> {
        try {
            await this.axiosInstance.delete(`/${id}`);
        } catch (error) {
            return Promise.reject(error);
        }
    }

    async bookEvent(eventId: number, ticketDto: TicketCreateRequest): Promise<Ticket> {
        try {
            const response = await this.axiosInstance.post(`/${eventId}/book`, ticketDto);
            return response.data;
        } catch (error) {
            return Promise.reject(error);
        }
    }



}

export default new EventService();