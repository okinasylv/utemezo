public class ScheduleTask{
    char betu;
    int marcpu; //maradék cpu löketidő
    int varakozas;

    public ScheduleTask(char c, int m, int v){
        betu=c;
        marcpu=m;
        varakozas=v;
    }
}