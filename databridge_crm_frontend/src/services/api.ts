import axios from "axios";

// Create an Axios instance with base URL for local development
const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8081/api/v1',
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request interceptor to add the JWT token to the Authorization header
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response interceptor to handle 401 Unauthorized errors globally
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401){
             // Token might be expired, log out logic could be triggered here
             localStorage.removeItem('token');
             // Ideally trigger a redirect or context action, but we'll leave it simple for now
            // window.location.href = '/login'; // Use with caution, prefer React Router
        }
        return Promise.reject(error);

    }  
);

export default api;