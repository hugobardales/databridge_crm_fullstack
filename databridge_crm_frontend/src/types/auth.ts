export interface User {
    sub: string
    roles: string[]
    exp: number;
}

export interface AuthResponse {
    token: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface RegisterRequest {
    email: string;
    password: string;
}