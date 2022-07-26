import { generiModel } from "./generi";

export interface booksModel{
  bookId: number,
  bookTitle: string,
  authorFname: string,
  authorLname: string,
  bookState: boolean,
  releasedYear: number | null,
  stockQuantity: number,
  pages: number,
  price: number,
  booksCategory: generiModel[],
  imgUrl: string
}
