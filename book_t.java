public enum book_t{
  FISICO("físico"),
  ONLINE("online");

  private String type_name;

  book_t(String type_name){
    this.type_name = type_name;
  }

  public String toString(){
    return this.type_name;
  }
};