export interface Service {
    id: number;
    name: string;
    description: string;
    price: number;
}

export function getEmptyService(): Service {
    return {
        id: null,
        name: null,
        description: null,
        price: null
    };
}

export function getService(id: number, name: string, description: string, price: number) {
    return {
        id,
        name,
        description,
        price
    };
}
