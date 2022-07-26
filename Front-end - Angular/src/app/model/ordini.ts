import { booksModel } from "./books";

export interface ordiniModel{
  ordineId: number,
  ordineData: Date,
  ordinePrice: number,
  bookListOrder: booksModel[]
  username: string
}
