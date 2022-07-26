import { booksModel } from "./books";

export interface cartModel{

  cartId: number,
  books: booksModel[]
}
