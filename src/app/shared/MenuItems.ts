import { Injectable } from "@angular/core";

export interface menu {
    state: string;
    name: string;
    type: string;
    icon: string;
    role: string;
}

const menu_items = [
    { state: 'dashboard', name: 'Dashboard', type: 'link', icon: 'dashboard', role: '' }
];

@Injectable()
export class MenuItems {
    getMenuItem(): menu[] {
        return menu_items;
    }
}
