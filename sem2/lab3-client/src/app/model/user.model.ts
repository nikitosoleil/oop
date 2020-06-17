export interface User {
    id: number;
    email: string;
    name: string;
    surname: string;
    blocked: boolean;
}

export function getEmptyUser(): User {
    return {
        id: null,
        email: null,
        name: null,
        surname: null,
        blocked: null
    };
}

export function getUser(id: number, email: string, name: string, surname: string, blocked: boolean) {
    return {
        id,
        email,
        name,
        surname,
        blocked
    };
}
