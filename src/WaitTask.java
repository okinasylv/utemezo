//várakozósor elemei
public class WaitTask {
    char betu; //betű
    int pri; //prioritás
    int st; //indítás
    int cpu; //CPU löketidő

    public WaitTask(char c, int p, int s, int cp){
        betu=c;
        pri=p;
        st=s;
        cpu=cp;
    }
}
