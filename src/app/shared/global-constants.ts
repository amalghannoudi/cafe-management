export class GlobalConstants{
    
    //Message
    public static genericError: string ="Somthing went wrong.Please try again later!" ; 
    public static unauthorized:string="You are not authorized person to acces this page";
    //Regex
    public static nameRegex: string = "[a-zA-Z0-9 ]*";
    public static emailRegex: string = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-z]{2,3}";
    public static contactRegex: string = "^[0-9]{8}$";
    public static passwordRegex: string = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{7,}$";


    //Variable
    public static error:string="error" ; 
   
    
}