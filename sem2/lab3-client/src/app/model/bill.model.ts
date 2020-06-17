export interface Bill {
    id: number;
    date: string;
    price: number;
    paid: boolean;
    email: string;
}

export function getEmptyBill(): Bill {
    return {
        id: null,
        date: null,
        price: null,
        paid: null,
        email: null
    };
}

export function getBill(id: number, date: string, price: number, paid: boolean, email: string) {
    return {
        id,
        date,
        price,
        paid,
        email
    };
}
