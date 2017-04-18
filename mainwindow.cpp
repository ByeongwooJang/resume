#include "mainwindow.h"

Game turn;
vector<Language> mmap;
Player players[4];
string user[4] = {"User_1","User_2","User_3","User_4"};
Level level[30];
Image_move user_move[4];
Turn_image T_img;
X_Y location[29];
int hardDiskCount[4];

vector<Language>::iterator it;

//vector<Language>::iterator it;
static int chk = 0;
int pro[4];
int What = -2;
int aaa = 1, level_size = 30;
int dice1= -1, dice2= -1;
int before_loc;
int flag = 0;
int computer = 0;
void insertMapInfo(string user[]);
int chk_mine(int index, string name);

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
{
    QPalette bkgnd;
    bkgnd.setBrush(this->backgroundRole(),QBrush(QImage("C:\\Users\\aolo26698824\\Desktop\\QT\\main.png")));

    for(int i = 0; i <4; i ++){
        hardDiskCount[i] = -1;
        pro[i] = 0;
    }
    hello = new QWidget;
    hello->setFixedSize(1280, 768);
    hello->setWindowTitle("start!");
    hello->setPalette(bkgnd);
    players[0].setName(user[0]);
    players[1].setName(user[1]);
    players[2].setName(user[2]);
    players[3].setName(user[3]);

    enter = new QPushButton("Start");

    enter->move(580,620);
    enter->setFixedSize(200,50);
    enter->setIconSize(QSize(300, 100));
    enter->setParent(hello);
    enter->show();
    connect(enter,SIGNAL(clicked(bool)),this,SLOT(join()));

    hello->setParent(this);
    hello->show();
    this->setPalette(bkgnd);
    //this->setParent(hello);
    resize(1280,768);
}
void MainWindow::join(){
    hello->close();
    mainWin();
}

void MainWindow::mainWin(){
    QPalette bkgnd;
    bkgnd.setBrush(this->backgroundRole(),QBrush(QImage("C:\\Users\\aolo26698824\\Desktop\\QT\\main3.png")));
    First = new QWidget;
    First->setFixedSize(1280, 768);
    First->setWindowTitle("start!");
    First->setPalette(bkgnd);
    //First->setPalette(bkgnd)

    insertMapInfo(user);
    p1 = new QPushButton("1P", this);
    p1->setFixedSize(100,50);
    p1->setIconSize(QSize(70, 70));
    p1->move(600 , 320);
    p1->setParent(First);

    p2 = new QPushButton("2P", this);
    p2->setFixedSize(100,50);
    p2->setIconSize(QSize(70, 70));
    p2->move(600 , 400);
    p2->setParent(First);

    p3 = new QPushButton("3P", this);
    p3->setFixedSize(100,50);
    p3->setIconSize(QSize(70, 70));
    p3->move(600 , 480);
    p3->setParent(First);

    p4 = new QPushButton("4P", this);
    p4->setFixedSize(100,50);
    p4->setIconSize(QSize(70, 70));
    p4->move(600 , 560);
    p4->setParent(First);

    inst = new QPushButton("Instruction", this);
    inst->setFixedSize(100,50);
    inst->setIconSize(QSize(70, 70));
    inst->move(600 , 640);
    inst->setParent(First);

    connect(p1,SIGNAL(clicked(bool)),this,SLOT(join1()));
    connect(p2,SIGNAL(clicked(bool)),this,SLOT(join2()));
    connect(p3,SIGNAL(clicked(bool)),this,SLOT(join3()));
    connect(p4,SIGNAL(clicked(bool)),this,SLOT(join4()));
    connect(inst,SIGNAL(clicked(bool)),this,SLOT(gotoInst()));
    buy = new QPushButton(tr("buy"), this);
    lv1 = new QPushButton(tr("lv1"), this);
    lv2 = new QPushButton(tr("lv2"), this);
    lv3 = new QPushButton(tr("lv3"), this);
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
    First->show();

}
void MainWindow::instruction(){
    QPalette bkgnd;
    bkgnd.setBrush(this->backgroundRole(),QBrush(QImage("C:\\Users\\aolo26698824\\Desktop\\QT\\instruction.png")));
    Instruction = new QWidget;
    Instruction->setFixedSize(1350, 768);
    Instruction->setWindowTitle("start!");
    Instruction->setPalette(bkgnd);

    back = new QPushButton("Back", this);
    back->setFixedSize(80,40);
    back->setIconSize(QSize(70, 70));
    back->move(1240 , 170);
    back->setParent(Instruction);
    back->show();

    connect(back,SIGNAL(clicked(bool)),this,SLOT(gotochoose()));
    Instruction->show();
}
void MainWindow::gotoInst(){
    instruction();
    First->close();
}
void MainWindow::gotochoose(){
    Instruction->close();
    mainWin();
}

void MainWindow::join4(){
    First->close();
    //turn(4);
    turn.setTurnSize(4);
    start();
    qDebug("START");
}
void MainWindow::join3(){
    First->close();
    turn.setTurnSize(3);
    start();
    qDebug("START");
}
void MainWindow::join2(){
    First->close();
    turn.setTurnSize(2);
    start();
    qDebug("START");
}
void MainWindow::join1(){
    First->close();
    turn.setTurnSize(2);
    players[0].setName("Computer");
    computer = 1;
    start();
    qDebug("START");
}
void MainWindow::start(){
    players[0].setGoldCard(1);
    players[1].setGoldCard(1);
    players[2].setGoldCard(1);
    players[3].setGoldCard(1);
    srand((unsigned int)time(NULL));
    moving_image_load();
    QPalette bkgnd;
    bkgnd.setBrush(this->backgroundRole(),QBrush(QImage("C:\\Users\\aolo26698824\\Desktop\\QT\\background.jpg")));

    startWidget = new QWidget;
    startWidget->setFixedSize(1280, 768);
    startWidget->setWindowTitle("start!");
    startWidget->setPalette(bkgnd);

    locateLevel();
    for(int i = 0; i < turn.getTurnSize(); i++){
        user_move[turn.getTurn(i)].stop->move(location[players[turn.getTurn(i)].getLocation()].x+(15*i),location[players[turn.getTurn(i)].getLocation()].y);
        user_move[turn.getTurn(i)].stop->setParent(startWidget);
        user_move[turn.getTurn(i)].stop->show();
    }
    dice_ = new QPushButton(tr("DICE"), this);
    dice_->setFixedSize(70,40);
    dice_->setIconSize(QSize(100, 70));
    dice_->move(420 ,680);
    dice_->setParent(startWidget);
    dice_->show();

    statue();

    connect(dice_,SIGNAL(clicked(bool)),this,SLOT(DiceStart()));

    startWidget->show();
    print_label = new QLabel();
    T_img.turn[0]->show();
    //resize(1280,768);
}

void MainWindow::statue(){
    QString sta[4];
    sta[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\status_1.png";
    sta[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\status_2.png";
    sta[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\status_3.png";
    sta[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\status_4.png";
    QString Tig[4];
    Tig[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\turn_1.png";
    Tig[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\turn_2.png";
    Tig[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\turn_3.png";
    Tig[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\turn_4.png";
    QFont font;
    font.setPixelSize(24);
    for(int i = 0; i <turn.getTurnSize(); i++){
        statues[i] = new QLabel(this);
        statues[i]->setPixmap(QPixmap(sta[i]));
        statues[i]->setScaledContents(true);
        statues[i]->move(40, 65 * (i + 1) + ( i * 68 ));
        statues[i]->setParent(startWidget);
        statues[i]->show();
        property[i] = new QLabel(tr("<font color='white'><font family : 맑은 고딕>Property : %1</font>").arg(pro[i]));
        property[i]->setFont(font);
        property[i]->move(110, 65 * (i + 1) + ( i * 68 ));
        property[i]->setParent(startWidget);
        property[i]->show();
        money[i] = new QLabel(tr("<font color='white'><font size = 26><font family : 맑은 고딕>Mable : %2</font>").arg(players[turn.getTurn(i)].getMoney()));
        money[i]->move(110, 65 * (i + 1) + ( i * 68 )+50);
        money[i]->setParent(startWidget);
        money[i]->show();
        T_img.turn[i] = new QLabel(this);
        T_img.turn[i]->setPixmap(QPixmap(Tig[i]));
        T_img.turn[i]->setScaledContents(true);
        T_img.turn[i]->setParent(startWidget);
        T_img.turn[i]->move(290, 65 * (i + 1) + ( i * 68 )-5);
        T_img.turn[i]->close();
        gdCard[i] = new QLabel(this);
        gdCard[i]->setPixmap(QPixmap("C:\\Users\\aolo26698824\\Desktop\\QT\\goldcard\\gd.png"));
        gdCard[i]->setScaledContents(true);
        gdCard[i]->setParent(startWidget);
        gdCard[i]->move(270, 65 * (i + 1) + ( i * 68 )-5);
        gdCard[i]->close();
    }
}
void MainWindow::update_statue(int index){
    QFont font;
    font.setPixelSize(24);
    if(players[turn.getTurn(index)].getDeath() != true){
        property[turn.getTurn(index)]->close();
        property[turn.getTurn(index)]->setText(tr("<font color='white'><font family : 맑은 고딕>Property : %2</font>").arg(pro[index]));
        property[turn.getTurn(index)]->setFont(font);
        property[turn.getTurn(index)]->show();
        cout<<"PROPERTY : "<<pro[index]<<endl;
        money[turn.getTurn(index)]->setText(tr("<font color='white'><font size = 26><font family : 맑은 고딕>Mable : %1</font>").arg(players[index].getMoney()));
        money[turn.getTurn(index)]->repaint();
    }else{
        property[turn.getTurn(index)]->setText(tr("<font color='white'><font family : 맑은 고딕>Bankrupt</font>"));
        property[turn.getTurn(index)]->repaint();
        money[turn.getTurn(index)]->close();
    }
    font.setPixelSize(16);
    int chk = 0, indexx = -1;
    for(int i =0; i < turn.getTurnSize(); i++){
        if(players[i].getDeath() != true){
            chk++;
            indexx = i;
        }
    }
    if(chk == 1){
        font.setPixelSize(36);
        winer = new QLabel(tr("<font color='white'><font family : 맑은 고딕>Winer is %1 Player!</font>").arg(indexx+1));
        winer->setFont(font);
        winer->move(80, 630);
        winer->setParent(startWidget);
        winer->show();
    }
}

void pt(){
    for(int i  = 0 ; i < mmap.size(); i ++){
        cout<<mmap[i].getName()<<", "<<mmap[i].getOwner()<<"|"<<endl;
    }
}
void MainWindow::usingGdCard(){
    Coner_ = new QWidget;
    Coner_->setFixedSize(400, 300);
    Coner_->setWindowTitle("start!");
    wdll = new QPushButton();

    QPixmap iimg("C:\\Users\\aolo26698824\\Desktop\\QT\\goldcard\\used.png");
    QIcon bticon(iimg);
    wdll->setIcon(bticon);

    wdll->setIconSize(QSize(400, 300));
    wdll->setParent(Coner_);
    wdll->show();
    Coner_->show();
    gdCard[turn.getTurn()]->close();
    players[turn.getTurn()].setGoldCard(-1);
    connect(wdll,SIGNAL(clicked(bool)),this,SLOT(gameExit_()));
}
void MainWindow::usingGdCard_(){
    Coner_ = new QWidget;
    Coner_->setFixedSize(400, 300);
    Coner_->setWindowTitle("start!");
    wdll = new QPushButton();

    QPixmap iimg("C:\\Users\\aolo26698824\\Desktop\\QT\\goldcard\\used.png");
    QIcon bticon(iimg);
    wdll->setIcon(bticon);

    wdll->setIconSize(QSize(400, 300));
    wdll->setParent(Coner_);
    wdll->show();
    Coner_->show();
    gdCard[turn.getTurn()]->close();
    players[turn.getTurn()].setGoldCard(-1);
    connect(wdll,SIGNAL(clicked(bool)),this,SLOT(gameExit_()));
}
void MainWindow::DiceStart(){
    ppp = new QLabel(this);
    ppp->close();

    chk = 0;
    cout<<"DICE ENTER!"<<endl<<endl;
    //pt();
    int k = 0;
    for(int i = 0; i < turn.getTurnSize(); i++){
        if(turn.getTurn(i) != -1){
            k = k + 1;
        }
    }
    if(k == 1)
        startWidget->close();
    for(int i = 0; i < turn.getTurnSize() ; i++){
       if(players[turn.getTurn(i)].getDeath() == 1 && players[turn.getTurn(i)].getDeathCheck() == false || players[turn.getTurn()].getMoney() <= 0){
           cout<<"%%%%%%%%%%%ENTER DEATH%%%%%%%%%%"<<endl;
           players[turn.getTurn(i)].setDeathCheck();
           for(int q = 0; q < mmap.size(); q++){
               if(mmap[q].getOwner() == players[turn.getTurn(i)].getName()){
                  mmap[q].initOwner();
                  cout<<"INIT : "<<mmap[q].getOwner()<<endl;
               }
           }
           for(int a = 0; a < players[turn.getTurn(i)].mine.size(); a++){
               players[turn.getTurn(i)].mine[a].initOwner();
           }
           for(int f = 0; f < players[turn.getTurn(i)].mine.size(); f++){
               level[players[turn.getTurn(i)].mine[f].getLocation()].flag[i]->close();
               level[players[turn.getTurn(i)].mine[f].getLocation()].level1->close();
               level[players[turn.getTurn(i)].mine[f].getLocation()].level2->close();
               level[players[turn.getTurn(i)].mine[f].getLocation()].level3->close();

           }
           players[turn.getTurn(i)].mine.resize(0);
           user_move[turn.getTurn(i)].stop->close();

           turn.MMturn(turn.getTurn(i)) ;

           break;
       }
    }

    for(int h = 0; h < turn.getTurnSize(); h++){
        if(turn.getTurn() == -1){
               turn.nextTurn();
        }else{
            break;
        }
    }
    T_img.turn[turn.getTurn()]->close();
    T_img.turn[turn.getNextTurn()]->show();
    cout<<"NOW TURN : "<<turn.getTurn()<<endl;
    cout<<endl<<"MAP VEC"<<endl;
    for(int i = 0; i < turn.getTurnSize(); i++){
        for(int a = 0; a < players[turn.getTurn(i)].mine.size(); a++){
            cout<<players[turn.getTurn(i)].getName()<<". "<<players[turn.getTurn(i)].mine[a].getName()<<" lv : "<<players[turn.getTurn(i)].mine[a].getLevel()<<endl;
        }
    }

    for(int i = 0; i < turn.getTurnSize(); i ++){
        if(turn.getTurn(i) == -1){

        }else
        cout<<i<<". "<<players[turn.getTurn(i)].getMoney()<<", "<<players[turn.getTurn(i)].getDeath()<<endl;
    }

    qApp->processEvents();

    dice1 = rand() % 3 + 1;//1~3
    dice2 = rand() % 3;//0~3
    static int savePoint = 0;
    /******************* 무인도 들어간거 캐릭터 출력하기 *****************/
    if(players[turn.getTurn()].getIntersection() == -1){
        if(players[turn.getTurn()].getGoldCard() != 2){
           if(savePoint == 0){
                players[turn.getTurn()].setSaveLoc(players[turn.getTurn()].getLocation());
                savePoint++;
            }else{
                savePoint++;
            }
            cout<<":HDC"<<hardDiskCount[turn.getTurn()]<<endl;
            if((hardDiskCount[turn.getTurn()] >=1 && hardDiskCount[turn.getTurn()] > -1) || dice1 == dice2){
                hardDiskCount[turn.getTurn()] = -1;
                savePoint = 0;
                players[turn.getTurn()].setIntersection(0);
                SStart();
            }else{
                user_move[turn.getTurn()].stop->repaint();
                user_move[turn.getTurn()].stop->move(location[22].x,location[1].y);
                user_move[turn.getTurn()].stop->show();
                hardDiskCount[turn.getTurn()]++;
                turn.nextTurn();
            }
        }else{
            players[turn.getTurn()].setIntersection(0);
            usingGdCard();
            SStart();
        }
    }else{
        SStart();
    }
}
void MainWindow::SStart(){
    int total = 0;
    QFont font;
    font.setPixelSize(24);
    font.setBold(true);

    print_label->repaint();
    print_label->move(520,680);
    print_label->setParent(startWidget);
    print_label->setText(tr("<font color='white'>%1 %2</font>").arg(dice1).arg(dice2));
    print_label->setFont(font);
    print_label->show();

    total += dice1+dice2;

    before_loc = moving(total);
    user_move[turn.getTurn()].stop->close();
    update_image();

    user_move[turn.getTurn()].stop->repaint();
    user_move[turn.getTurn()].stop->move(location[players[turn.getTurn()].getLocation()].x+(15*turn.getTurn()),location[players[turn.getTurn()].getLocation()].y);
    user_move[turn.getTurn()].stop->show();

    menu();

    turn.nextTurn();
}

void updateOwner(int index, string name,Language newR){
    for(int i = 0; i < players[turn.getTurn(index)].mine.size(); i++){
        if(name == players[turn.getTurn(index)].mine[i].getName()){
            players[index].mine[i] = newR;
        }
    }
}
int chk_mine(int index, string name){
    for(int i = 0; i < players[turn.getTurn(index)].mine.size(); i++){
        if(name == players[turn.getTurn(index)].mine[i].getName()){
            return 1;
        }
    }
    return 0;
}
int find_owner(string Land_name){
    for(int a = 0; a < turn.getTurnSize(); a++){
        for(int i = 0; i < players[turn.getTurn(a)].mine.size(); i++){
            if(Land_name == players[turn.getTurn(a)].mine[i].getName()){
                return a;
            }
        }
    }
    return -1;
}
int find_land(int index, string Land_name){
   for(int i = 0; i < players[turn.getTurn(index)].mine.size(); i++){
        if(Land_name == players[turn.getTurn(index)].mine[i].getName()){
            return i;
        }
    }
    return -1;
}

void MainWindow::menu(){
    cout<<"MENU ENTER!!!!"<<endl;

    int index = turn.getTurn();
    int loc = players[index].getLocation();

    Language mapp = mmap.at(loc);


    dice_->close();
    close = new QPushButton(tr("exit"), this);
    close->setFixedSize(70,40);
    close->setIconSize(QSize(100, 70));
    close->move(620 ,680);
    close->setParent(startWidget);
    close->show();

    level[29].flag[0]->move(730,640);
    level[29].flag[0]->show();

    buy->setText(tr("%1").arg(mapp.getPrice()));
    buy->setFixedSize(70,40);
    buy->setIconSize(QSize(100, 70));
    buy->move(700 ,680);
    buy->setParent(startWidget);
    buy->repaint();
    buy->show();

    level[29].level1->move(800,640);
    level[29].level1->show();
    lv1->setText(tr("%1").arg(mapp.getPrice(1)));
    lv1->setFixedSize(70,40);
    lv1->setIconSize(QSize(100, 70));
    lv1->move(780 ,680);
    lv1->setParent(startWidget);
    lv1->show();

    level[29].level2->move(880,640);
    level[29].level2->show();
    lv2->setText(tr("%1").arg(mapp.getPrice(2)));
    lv2->setFixedSize(70,40);
    lv2->setIconSize(QSize(100, 70));
    lv2->move(860 ,680);
    lv2->setParent(startWidget);
    lv2->show();

    level[29].level3->move(960,640);
    level[29].level3->show();
    lv3->setText(tr("%1").arg(mapp.getPrice(3)));
    lv3->setFixedSize(70,40);
    lv3->setIconSize(QSize(100, 70));
    lv3->move(940 ,680);
    lv3->setParent(startWidget);
    lv3->show();
    connect(close,SIGNAL(clicked(bool)),this,SLOT(close_()));

    cout<<"Computer : "<<computer<<", turn : "<<turn.getTurn()<<endl;
    cout<<"GOld : "<<players[turn.getTurn()].getGoldCard()<<", Map Owner : "<<mapp.getOwner()<<endl;
    if(computer == 1 && turn.getTurn() == 0){
        cout<<"!!!!!!!!!!!!Computer Turn!!!!!!"<<endl;
        if(players[index].getMoney() >= 60){
            cout<<"Computer Somthing"<<endl;
            if(mapp.getOwner()== " " && mapp.getNoupgrade() != 2){
                buy__();
                lv1->close();
                lv2->close();
                lv3->close();
            }else if(chk_mine(index, mapp.getName()) && mapp.getNoupgrade() == 0){//자기 땅이면
                switch(mapp.getLevel()){//땅 레벨별로 출력
                    case 0:
                        lv__1();
                        break;
                    case 1:
                        lv__2();
                        break;
                    case 2:
                        lv__3();
                        break;
                    default:
                        dice_->show();
                        buy->close();
                        lv1->close();
                        lv2->close();
                        lv3->close();
                        cout<<"MAX LEVEL"<<endl;
                        return;
                }
            }else if(chk_mine(index, mapp.getName()) != 1 && mapp.getNoupgrade() != 2){
                if(players[index].getGoldCard() != 1){
                    int owner_index = find_owner(mapp.getName());
                    int finded_map_index = find_land(owner_index,mapp.getName());
                    int Price = players[owner_index].mine[finded_map_index].getHacking();
                    if(players[index].getMoney() - Price > 0){
                        cout<<"CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC"<<endl;
                        players[index].M_Money(Price);
                        players[owner_index].P_Money(Price);
                    }else{
                        players[index].M_Money(Price);
                        cout<<"BROKEN!"<<endl;
                        players[index].setDeath();
                    }
                    update_statue(owner_index);
                    QString str[9];
                    str[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get1.png";
                    str[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get2.png";
                    str[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get3.png";
                    str[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get4.png";
                    str[4] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get5.png";
                    str[5] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get6.png";
                    str[6] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get7.png";
                    str[7] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get8.png";
                    str[8] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get9.png";

                    QString str_[10];
                    str_[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost1.png";
                    str_[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost2.png";
                    str_[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost3.png";
                    str_[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost4.png";
                    str_[4] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost5.png";
                    str_[5] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost6.png";
                    str_[6] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost7.png";
                    str_[7] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost8.png";
                    str_[8] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost9.png";
                    str_[9] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost10.png";

                    for(int i =0; i <10; i++){
                        ppp->repaint();
                        ppp->setPixmap(QPixmap(str_[i]));
                        ppp->setScaledContents(true);
                        ppp->setParent(startWidget);
                        ppp->move(location[players[turn.getTurn()].getLocation()].x,location[players[turn.getTurn()].getLocation()].y-30);
                        ppp->show();
                        Sleep(100);
                    }
                        ppp->close();
                    for(int i = 0; i < 9; i++){
                        ppp->repaint();
                        ppp->setPixmap(QPixmap(str[i]));
                        ppp->setScaledContents(true);
                        ppp->setParent(startWidget);
                        ppp->move(location[players[owner_index].getLocation()].x,location[players[owner_index].getLocation()].y-50);
                        ppp->show();
                        Sleep(100);
                    }

                    ppp->close();
                }else{
                    QString str__[21];
                    str__[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd1.png";
                    str__[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd2.png";
                    str__[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd3.png";
                    str__[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd4.png";
                    str__[4] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd5.png";
                    str__[5] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd6.png";
                    str__[6] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd7.png";
                    str__[7] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd8.png";
                    str__[8] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd9.png";
                    str__[9] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd10.png";
                    str__[10] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd11.png";
                    str__[11] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd12.png";
                    str__[12] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd13.png";
                    str__[13] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd14.png";
                    str__[14] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd15.png";
                    str__[15] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd16.png";
                    str__[16] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd17.png";
                    str__[17] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd18.png";
                    str__[18] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd19.png";
                    str__[19] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd20.png";
                    str__[20] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd21.png";

                    for(int i = 0; i < 21; i++){
                        ppp->repaint();
                        ppp->setPixmap(QPixmap(str__[i]));
                        ppp->setScaledContents(true);
                        ppp->setParent(startWidget);
                        ppp->move(location[players[turn.getTurn()].getLocation()].x-30,location[players[turn.getTurn()].getLocation()].y-20);
                        ppp->show();
                        Sleep(110);
                    }
                    usingGdCard_();
                    players[turn.getTurn()].setGoldCard(-1);
                    ppp->close();
                }
                update_statue(index);
                buy->close();
                lv1->close();
                lv2->close();
                lv3->close();
                dice_->show();
            }else{
                dice_->show();
                buy->close();
                lv1->close();
                lv2->close();
                lv3->close();
            }
        }else{
            dice_->show();
            buy->close();
            lv1->close();
            lv2->close();
            lv3->close();
            return;
        }
    }else{
        cout<<"ELSE ENTER"<<endl;
        if(mapp.getOwner()== " " && mapp.getNoupgrade() != 2){
            connect(buy,SIGNAL(clicked(bool)),this,SLOT(buy_()));
            lv1->close();
            lv2->close();
            lv3->close();
            flag = 1;
        }else if(chk_mine(index, mapp.getName()) && mapp.getNoupgrade() == 0){//자기 땅이면
            switch(mapp.getLevel()){//땅 레벨별로 출력
                case 0:
                    buy->close();
                    lv2->close();
                    lv3->close();
                    connect(lv1,SIGNAL(clicked(bool)),this,SLOT(lv_1())) ;
                    break;
                case 1:
                    buy->close();
                    lv1->close();
                    lv3->close();
                    connect(lv2,SIGNAL(clicked(bool)),this,SLOT(lv_2()));
                    break;
                case 2:
                    buy->close();
                    lv1->close();
                    lv2->close();
                    connect(lv3,SIGNAL(clicked(bool)),this,SLOT(lv_3()));
                    break;
                default:
                    buy->close();
                    lv1->close();
                    lv2->close();
                    lv3->close();
                    dice_->show();
                    cout<<"MAX LEVEL"<<endl;
            }
        }else if(chk_mine(index, mapp.getName()) != 1 && mapp.getNoupgrade() != 2){
            if(players[index].getGoldCard() != 1){
                int owner_index = find_owner(mapp.getName());
                int finded_map_index = find_land(owner_index,mapp.getName());
                int Price = players[owner_index].mine[finded_map_index].getHacking();
                if(players[index].getMoney() - Price > 0){
                    cout<<"CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC"<<endl;
                    players[index].M_Money(Price);
                    players[owner_index].P_Money(Price);
                }else{
                    players[index].M_Money(Price);
                    cout<<"BROKEN!"<<endl;
                    players[index].setDeath();
                }
                update_statue(owner_index);
                QString str[9];
                str[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get1.png";
                str[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get2.png";
                str[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get3.png";
                str[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get4.png";
                str[4] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get5.png";
                str[5] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get6.png";
                str[6] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get7.png";
                str[7] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get8.png";
                str[8] = "C:\\Users\\aolo26698824\\Desktop\\QT\\getM\\get9.png";

                QString str_[10];
                str_[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost1.png";
                str_[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost2.png";
                str_[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost3.png";
                str_[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost4.png";
                str_[4] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost5.png";
                str_[5] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost6.png";
                str_[6] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost7.png";
                str_[7] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost8.png";
                str_[8] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost9.png";
                str_[9] = "C:\\Users\\aolo26698824\\Desktop\\QT\\lostM\\lost10.png";

                for(int i =0; i <10; i++){
                    ppp->repaint();
                    ppp->setPixmap(QPixmap(str_[i]));
                    ppp->setScaledContents(true);
                    ppp->setParent(startWidget);
                    ppp->move(location[players[turn.getTurn()].getLocation()].x,location[players[turn.getTurn()].getLocation()].y-30);
                    ppp->show();
                    Sleep(100);
                }
                    ppp->close();
                for(int i = 0; i < 9; i++){
                    ppp->repaint();
                    ppp->setPixmap(QPixmap(str[i]));
                    ppp->setScaledContents(true);
                    ppp->setParent(startWidget);
                    ppp->move(location[players[owner_index].getLocation()].x,location[players[owner_index].getLocation()].y-50);
                    ppp->show();
                    Sleep(100);
                }

                ppp->close();
            }else{
                QString str__[21];
                str__[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd1.png";
                str__[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd2.png";
                str__[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd3.png";
                str__[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd4.png";
                str__[4] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd5.png";
                str__[5] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd6.png";
                str__[6] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd7.png";
                str__[7] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd8.png";
                str__[8] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd9.png";
                str__[9] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd10.png";
                str__[10] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd11.png";
                str__[11] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd12.png";
                str__[12] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd13.png";
                str__[13] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd14.png";
                str__[14] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd15.png";
                str__[15] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd16.png";
                str__[16] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd17.png";
                str__[17] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd18.png";
                str__[18] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd19.png";
                str__[19] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd20.png";
                str__[20] = "C:\\Users\\aolo26698824\\Desktop\\QT\\usingGdCd\\gdcd21.png";

                for(int i = 0; i < 21; i++){
                    ppp->repaint();
                    ppp->setPixmap(QPixmap(str__[i]));
                    ppp->setScaledContents(true);
                    ppp->setParent(startWidget);
                    ppp->move(location[players[turn.getTurn()].getLocation()].x-30,location[players[turn.getTurn()].getLocation()].y-20);
                    ppp->show();
                    Sleep(110);
                }
                usingGdCard_();
                ppp->close();
            }
            update_statue(index);
            buy->close();
            lv1->close();
            lv2->close();
            lv3->close();
            dice_->show();
        }else{
            dice_->show();
            buy->close();
            lv1->close();
            lv2->close();
            lv3->close();
        }
    }
}

void MainWindow::buy__(){
    chk++;
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
    dice_->close();
    cout<<"BUY INDEX : "<<turn.getTurn()<<endl;
    int location = players[turn.getTurn()].getLocation();
    cout<<"Computer's Now Location : "<<location<<endl;
    Language mapp = mmap.at(location);
    if(chk == 1){
        if(mapp.getOwner() == " " && mapp.getNoupgrade() != 2){
            if((players[turn.getTurn()].getMoney() - mapp.getPrice(0)) > 0){//소지금액이 땅가격보다 많을시 진행
                pro[turn.getTurn()]+=1;
                players[turn.getTurn()].M_Money(mapp.getPrice());//주인없고 땅 사려면 땅가격만큼 돈에서 뺸다.
                mapp.setOwner("Computer");//맵 주인을 바꿔준다.
                players[turn.getTurn()].mine.push_back(mapp);
                level[location].flag[turn.getTurn()]->show();
                mmap.at(location) = mapp;
                }else{
                cout<<"lack money!... now Mon :"<<players[turn.getTurn()].getMoney()<<endl;
            }
        }
    }
    update_statue(turn.getTurn());
    dice_->show();
}
void MainWindow::buy_(){
    chk++;
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
    dice_->close();
    cout<<"BUY INDEX : "<<turn.getBeforeTurn()<<endl;
    int location = players[turn.getBeforeTurn()].getLocation();
    Language mapp = mmap.at(location);
    if(chk == 1){
        if(mapp.getOwner() == " " && mapp.getNoupgrade() != 2){
            if((players[turn.getBeforeTurn()].getMoney() - mapp.getPrice(0)) > 0){//소지금액이 땅가격보다 많을시 진행
                pro[turn.getBeforeTurn()]+=1;
                players[turn.getBeforeTurn()].M_Money(mapp.getPrice());//주인없고 땅 사려면 땅가격만큼 돈에서 뺸다.
                mapp.setOwner(players[turn.getBeforeTurn()].getName());//맵 주인을 바꿔준다.
                players[turn.getBeforeTurn()].mine.push_back(mapp);
                level[location].flag[turn.getBeforeTurn()]->show();
                mmap.at(location) = mapp;
                }else{
                cout<<"lack money!... now Mon :"<<players[turn.getBeforeTurn()].getMoney()<<endl;
            }
        }
    }
    update_statue(turn.getBeforeTurn());
    flag = 0;
    dice_->show();
}

void MainWindow::lv_1(){
    chk++;
    dice_->close();
    int location = players[turn.getBeforeTurn()].getLocation();
    Language mapp = mmap.at(location);
    if(chk == 1){
        if((players[turn.getBeforeTurn()].getMoney() - mapp.getPrice(1)) > 0){//돈을 충분히 갖고 있으면
            mapp.upLevel();
            players[turn.getBeforeTurn()].M_Money(mapp.getPrice());//돈을 감한다.
            pro[turn.getBeforeTurn()]+=3;
            mapp.upHacking();
            level[location].level1->show();
            mmap.at(location) = mapp;
            updateOwner(turn.getBeforeTurn(), mapp.getName(),mapp);
        }else{
            cout<<"lack money"<<endl;
        }
    }

    update_statue(turn.getBeforeTurn());
    dice_->show();
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
}

void MainWindow::lv_2(){
    dice_->close();
    chk++;
    int location = players[turn.getBeforeTurn()].getLocation();
    Language mapp = mmap.at(location);
    cout<<turn.getBeforeTurn()<<". "<<"LV2 Price : "<<mapp.getPrice(2)<<" , "<<players[turn.getBeforeTurn()].getMoney()<<endl;
    if(chk == 1){
        if((players[turn.getBeforeTurn()].getMoney() - mapp.getPrice(2)) > 0){//돈을 충분히 갖고 있으면
            mapp.upLevel();
            players[turn.getBeforeTurn()].M_Money(mapp.getPrice());//돈을 감한다.
            pro[turn.getBeforeTurn()]+=5;
            mapp.upHacking();
            level[location].level1->close();
            level[location].level2->show();
            mmap.at(location) = mapp;
            updateOwner(turn.getBeforeTurn(), mapp.getName(),mapp);
        }else{
            cout<<"lack money"<<endl;
        }
    }
    update_statue(turn.getBeforeTurn());
    dice_->show();
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
}

void MainWindow::lv_3(){
    chk++;
    dice_->close();
    int location = players[turn.getBeforeTurn()].getLocation();
    Language mapp = mmap.at(location);
    cout<<turn.getBeforeTurn()<<". "<<"LV3 Price : "<<mapp.getPrice(3)<<" , "<<players[turn.getBeforeTurn()].getMoney()<<endl;
    if(chk == 1){
        if((players[turn.getBeforeTurn()].getMoney() - mapp.getPrice(3)) > 0){//돈을 충분히 갖고 있으면
            mapp.upLevel();
            players[turn.getBeforeTurn()].M_Money(mapp.getPrice());//돈을 감한다.
            pro[turn.getBeforeTurn()]+=9;
            mapp.upHacking();
            level[location].level2->close();
            level[location].level3->show();
            mmap.at(location) = mapp;
            updateOwner(turn.getBeforeTurn(), mapp.getName(), mapp);
        }else{
            cout<<"lack money"<<endl;
        }
    }
    update_statue(turn.getBeforeTurn());
    dice_->show();
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
}

void MainWindow::lv__1(){
    chk++;
    dice_->close();
    int location = players[turn.getTurn()].getLocation();
    Language mapp = mmap.at(location);
    if(chk == 1){
        if((players[turn.getTurn()].getMoney() - mapp.getPrice(1)) > 0){//돈을 충분히 갖고 있으면
            mapp.upLevel();
            players[turn.getTurn()].M_Money(mapp.getPrice());//돈을 감한다.
            pro[turn.getTurn()]+=3;
            mapp.upHacking();
            level[location].level1->show();
            mmap.at(location) = mapp;
            updateOwner(turn.getTurn(), mapp.getName(),mapp);
        }else{
           cout<<"lack money"<<endl;
        }
    }
    update_statue(turn.getTurn());
    dice_->show();
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
}

void MainWindow::lv__2(){
    dice_->close();
    chk++;
    int location = players[turn.getTurn()].getLocation();
    Language mapp = mmap.at(location);
    cout<<turn.getTurn()<<". "<<"LV2 Price : "<<mapp.getPrice(2)<<" , "<<players[turn.getTurn()].getMoney()<<endl;
    if(chk == 1){
        if((players[turn.getTurn()].getMoney() - mapp.getPrice(2)) > 0){//돈을 충분히 갖고 있으면
            mapp.upLevel();
            players[turn.getTurn()].M_Money(mapp.getPrice());//돈을 감한다.
            pro[turn.getTurn()]+=5;
            mapp.upHacking();
            level[location].level1->close();
            level[location].level2->show();
            mmap.at(location) = mapp;
            updateOwner(turn.getTurn(), mapp.getName(),mapp);
        }else{
            cout<<"lack money"<<endl;
        }
    }
    update_statue(turn.getTurn());
    dice_->show();
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
}

void MainWindow::lv__3(){
    chk++;
    dice_->close();
    int location = players[turn.getTurn()].getLocation();
    Language mapp = mmap.at(location);
    cout<<turn.getTurn()<<". "<<"LV3 Price : "<<mapp.getPrice(3)<<" , "<<players[turn.getTurn()].getMoney()<<endl;
    if(chk == 1){
        if((players[turn.getTurn()].getMoney() - mapp.getPrice(3)) > 0){//돈을 충분히 갖고 있으면
            mapp.upLevel();
            players[turn.getTurn()].M_Money(mapp.getPrice());//돈을 감한다.
            pro[turn.getTurn()]+=9;
            mapp.upHacking();
            level[location].level2->close();
            level[location].level3->show();
            mmap.at(location) = mapp;
            updateOwner(turn.getTurn(), mapp.getName(), mapp);
        }else{
            cout<<"lack money"<<endl;
        }
    }
    update_statue(turn.getTurn());
    dice_->show();
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
}
int MainWindow::conerGame(){
    int game = 0, user = 0;
    What = -2;
    dice_->close();
    game = rand()%3;
    user = rand()%3;
    if((game == 0 && user == 1) || (game == 1 && user == 2) || (game == 2 && user == 0)){
        What = 1;
    }else if((game == 0 && user == 0) || (game == 1 && user == 1) || (game == 2 && user ==2)){
        What = 0;
    }else{
        What = -1;
    }
    if(computer == 1 && turn.getTurn() == 0){
        chk_win_();
    }else{
    Coner = new QWidget;
    Coner->setFixedSize(400, 300);
    Coner->setWindowTitle("start!");
    cout<<"CONERGAME"<<endl;

    QString srp[3];
    srp[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\scissors.png";
    srp[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\rock.png";
    srp[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\paper.png";

    for(int i = 0; i < 3; i ++){
        SRP[i] = new QLabel(this);
        SRP[i]->setPixmap(QPixmap(srp[i]));
        SRP[i]->setScaledContents(true);
        SRP[i]->setParent(Coner);

    }
    SRP[0]->move(20,50);
    SRP[1]->move(140,50);
    SRP[2]->move(260,50);

    rock = new QPushButton(tr("scissors"), this);
    rock->setFixedSize(70,40);
    rock->setIconSize(QSize(100, 70));
    rock->move(40 ,200);
    rock->setParent(Coner);
    rock->show();

    paper = new QPushButton(tr("rock"), this);
    paper->setFixedSize(70,40);
    paper->setIconSize(QSize(100, 70));
    paper->move(160 ,200);
    paper->setParent(Coner);
    paper->show();

    scissors = new QPushButton(tr("paper"), this);
    scissors->setFixedSize(70,40);
    scissors->setIconSize(QSize(100, 70));
    scissors->move(280 ,200);
    scissors->setParent(Coner);
    scissors->show();

    exit = new QPushButton(tr("Exit"), this);
    exit->setFixedSize(50,30);
    exit->setIconSize(QSize(50, 30));
    exit->move(40 ,260);
    exit->setParent(Coner);
    exit->show();

    Coner->connect(exit,SIGNAL(clicked(bool)),this,SLOT(gameExit()));
    Coner->connect(rock,SIGNAL(clicked(bool)),this,SLOT(rock_()));
    Coner->connect(scissors,SIGNAL(clicked(bool)),this,SLOT(scissors_()));
    Coner->connect(paper,SIGNAL(clicked(bool)),this,SLOT(paper_()));
    Coner->show();
    }
    return What;
}
void MainWindow::gameExit(){
    dice_->show();
    if(What == 1 || What == 0)//황금카드수령을 보여주기  위해 비겨도 수령하도록 함.**************************
        goldCard();
    Coner->close();
}
void MainWindow::gameExit_(){
    dice_->show();
    Coner_->close();
}
void MainWindow::gameExit__(){
    dice_->show();
    if(What == 1 || What == 0)//황금카드수령을 보여주기  위해 비겨도 수령하도록 함.***************************
        goldCard();
    Coner__->close();
}
void MainWindow::chk_win_(){
    cout<<"CHK_ WIN ENTER !!! Mother Fuckjer"<<endl;
    Coner__ = new QWidget;
    Coner__->setFixedSize(400, 300);
    Coner__->setWindowTitle("start!");
    cout<<"CONERGAME"<<endl;
    wdl = new QPushButton();
    if(What == 1){
        QPixmap iimg("C:\\Users\\aolo26698824\\Desktop\\QT\\win.png");
        QIcon bticon(iimg);
        wdl->setIcon(bticon);
        wdl->setIconSize(QSize(400, 300));
        cout<<"WIN"<<endl;
    }else if(What == 0){
        QPixmap iimg("C:\\Users\\aolo26698824\\Desktop\\QT\\draw.png");
        QIcon bticon(iimg);
        wdl->setIcon(bticon);
        wdl->setIconSize(QSize(400, 300));
        cout<<"DRAW"<<endl;
    }else{
        QPixmap iimg("C:\\Users\\aolo26698824\\Desktop\\QT\\lose.png");
        QIcon bticon(iimg);
        wdl->setIcon(bticon);
        wdl->setIconSize(QSize(400, 300));
        cout<<"LOSE"<<endl;
    }
    wdl->setParent(Coner__);
    wdl->show();
    Coner__->show();
    connect(wdl,SIGNAL(clicked(bool)),this,SLOT(gameExit__()));
}
void MainWindow::chk_win(){

    wdl = new QPushButton();
    if(What == 1){
        QPixmap iimg("C:\\Users\\aolo26698824\\Desktop\\QT\\win.png");
        QIcon bticon(iimg);
        wdl->setIcon(bticon);
        wdl->setIconSize(QSize(400, 300));
        cout<<"WIN"<<endl;
    }else if(What == 0){
        QPixmap iimg("C:\\Users\\aolo26698824\\Desktop\\QT\\draw.png");
        QIcon bticon(iimg);
        wdl->setIcon(bticon);
        wdl->setIconSize(QSize(400, 300));
        cout<<"DRAW"<<endl;
    }else{
        QPixmap iimg("C:\\Users\\aolo26698824\\Desktop\\QT\\lose.png");
        QIcon bticon(iimg);
        wdl->setIcon(bticon);
        wdl->setIconSize(QSize(400, 300));
        cout<<"LOSE"<<endl;
    }
    wdl->setParent(Coner);
    wdl->show();

    connect(wdl,SIGNAL(clicked(bool)),this,SLOT(gameExit()));
}

void MainWindow::rock_(){
    chk_win();
}

void MainWindow::scissors_(){
    chk_win();
}

void MainWindow::paper_(){
    chk_win();
}

void MainWindow::goldCard(){
    Coner_ = new QWidget;
    Coner_->setFixedSize(400, 300);
    Coner_->setWindowTitle("start!");

    int random = rand()%5+1;
    wdll = new QPushButton();

    QString str[6];
    str[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\goldcard\\gld1.png";
    str[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\goldcard\\gld2.png";
    str[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\goldcard\\gld3.png";
    str[4] = "C:\\Users\\aolo26698824\\Desktop\\QT\\goldcard\\gld4.png";
    str[5] = "C:\\Users\\aolo26698824\\Desktop\\QT\\goldcard\\gld3.png";
    cout<<"random goldCard : "<<random<<endl;
    switch(random){
        case 3: case 5:
            players[turn.getBeforeTurn()].P_Money(70);
            cout<<turn.getBeforeTurn()<<". PPPP money"<<"  "<<players[turn.getBeforeTurn()].getMoney();
            break;
        case 4:
            if(players[turn.getBeforeTurn()].getMoney() - 50 > 0)
                players[turn.getBeforeTurn()].M_Money(50);

            cout<<turn.getBeforeTurn()<<". MMMM money"<<"  "<<players[turn.getBeforeTurn()].getMoney();
            break;
        default:
            players[turn.getBeforeTurn()].setGoldCard(random);
    }
    update_statue(turn.getBeforeTurn());
    if(players[turn.getBeforeTurn()].getGoldCard() == 1 || players[turn.getBeforeTurn()].getGoldCard() == 2 ){
        gdCard[turn.getBeforeTurn()]->show();
    }
    QPixmap iimg(str[random]);
    QIcon bticon(iimg);
    wdll->setIcon(bticon);

    wdll->setIconSize(QSize(400, 300));
    wdll->setParent(Coner_);
    wdll->show();
    Coner_->show();
    connect(wdll,SIGNAL(clicked(bool)),this,SLOT(gameExit_()));
}

void MainWindow::update_image(){
    int x = location[before_loc].x, y = location[before_loc].y;
    int now = players[turn.getTurn()].getLocation();
    int i = 0;
    user_move[turn.getTurn()].stop->close();
    user_move[turn.getTurn()].move[0]->move(x,y);
    user_move[turn.getTurn()].move[1]->move(x,y);
    user_move[turn.getTurn()].move[0]->setParent(startWidget);
    user_move[turn.getTurn()].move[1]->setParent(startWidget);
    user_move[turn.getTurn()].reverse_move[0]->setParent(startWidget);
    user_move[turn.getTurn()].reverse_move[1]->setParent(startWidget);
    user_move[turn.getTurn()].move[0]->show();
    user_move[turn.getTurn()].move[1]->show();
    if(before_loc >= 0 && before_loc <= 4 && now <= 5){
        if(now <= 5){
            for(; y >= location[now].y; y-= 5, i++){
                switch(i%2){
                    case 0:
                        user_move[turn.getTurn()].move[0]->move(x,y);
                        user_move[turn.getTurn()].move[0]->repaint();

                        break;
                    case 1:
                        user_move[turn.getTurn()].move[1]->move(x,y);
                        user_move[turn.getTurn()].move[1]->repaint();

                }
                Sleep(11);
            }
        }
    }else if(before_loc >= 0 && before_loc <= 10 && now <= 10){
        if(before_loc <= 5){
            for(; y >= location[5].y; y-= 5, i++){
                switch(i%2){
                    case 0:
                        user_move[turn.getTurn()].move[0]->move(x,y);
                        user_move[turn.getTurn()].move[0]->repaint();

                        break;
                    case 1:
                        user_move[turn.getTurn()].move[1]->move(x,y);
                        user_move[turn.getTurn()].move[1]->repaint();

                }
                Sleep(11);
            }
        }
        int tmp = location[now].x;
        for(; tmp <= x; x-= 5, i++){
            switch(i%2){
                case 0:

                    user_move[turn.getTurn()].move[0]->move(x,y);
                    user_move[turn.getTurn()].move[0]->repaint();
                    break;
                case 1:
                    user_move[turn.getTurn()].move[1]->move(x,y);
                    user_move[turn.getTurn()].move[1]->repaint();

            }
            Sleep(11);
        }

    }else if(before_loc >= 6 && before_loc <= 15 && now <= 15){
        if(before_loc <= 10){
            int tmp = location[now].x;
            for(; tmp <= x; x-= 5, i++){
                switch(i%2){
                    case 0:
                        user_move[turn.getTurn()].move[0]->move(x,y);
                        user_move[turn.getTurn()].move[0]->repaint();
                        break;
                    case 1:
                        user_move[turn.getTurn()].move[1]->move(x,y);
                        user_move[turn.getTurn()].move[1]->repaint();
                }
                Sleep(11);
            }
        }
        user_move[turn.getTurn()].move[0]->close();
        user_move[turn.getTurn()].move[1]->close();
        user_move[turn.getTurn()].reverse_move[0]->move(x,y);
        user_move[turn.getTurn()].reverse_move[1]->move(x,y);
        user_move[turn.getTurn()].reverse_move[0]->show();
        user_move[turn.getTurn()].reverse_move[1]->show();
        for(; y <= location[now].y; y+= 5, i++){
            switch(i%2){
                case 0:
                    user_move[turn.getTurn()].reverse_move[0]->move(x,y);
                    user_move[turn.getTurn()].reverse_move[0]->repaint();
                    break;
                case 1:
                    user_move[turn.getTurn()].reverse_move[1]->move(x,y);
                    user_move[turn.getTurn()].reverse_move[1]->repaint();
            }
            Sleep(11);
        }

    }else if(before_loc >= 11 && before_loc <= 19 && (now <= 19 || now == 0)){
        user_move[turn.getTurn()].move[0]->close();
        user_move[turn.getTurn()].move[1]->close();
        if(before_loc < 15 && now != 0){
            user_move[turn.getTurn()].reverse_move[0]->move(x,y);
            user_move[turn.getTurn()].reverse_move[1]->move(x,y);
            user_move[turn.getTurn()].reverse_move[0]->show();
            user_move[turn.getTurn()].reverse_move[1]->show();
            for(; y <= location[now].y; y+= 5, i++){
                switch(i%2){
                    case 0:
                        user_move[turn.getTurn()].reverse_move[0]->move(x,y);
                        user_move[turn.getTurn()].reverse_move[0]->repaint();
                        break;
                    case 1:
                        user_move[turn.getTurn()].reverse_move[1]->move(x,y);
                        user_move[turn.getTurn()].reverse_move[1]->repaint();
                }
                Sleep(11);
            }
        }
        user_move[turn.getTurn()].reverse_move[0]->close();
        user_move[turn.getTurn()].reverse_move[1]->close();

        user_move[turn.getTurn()].reverse_move[0]->show();
        user_move[turn.getTurn()].reverse_move[1]->show();
        cout<<"before x :"<<location[before_loc].x<<", now x :"<<location[now].x<<endl;
        int tmp = location[now].x;
        for(; tmp >= x; x+= 5, i++){
            switch(i%2){
                case 0:
                    user_move[turn.getTurn()].reverse_move[0]->move(x,y);
                    user_move[turn.getTurn()].reverse_move[0]->repaint();
                    break;
                case 1:
                    user_move[turn.getTurn()].reverse_move[1]->move(x,y);
                    user_move[turn.getTurn()].reverse_move[1]->repaint();
            }
            Sleep(11);
        }
        user_move[turn.getTurn()].reverse_move[0]->close();
        user_move[turn.getTurn()].reverse_move[1]->close();
        user_move[turn.getTurn()].move[0]->show();
        user_move[turn.getTurn()].move[1]->show();
        if(before_loc <= 19 && before_loc >= 16 && now <= 5){
            for(; y >= location[now].y; y-= 5, i++){
                switch(i%2){
                    case 0:
                        user_move[turn.getTurn()].move[0]->move(x,y);
                        user_move[turn.getTurn()].move[0]->repaint();
                        break;
                    case 1:
                        user_move[turn.getTurn()].move[1]->move(x,y);
                        user_move[turn.getTurn()].move[1]->repaint();
                }
                Sleep(11);
            }
        }
    }else if(before_loc == 5 || (before_loc >= 20 && before_loc <= 24)){
        for(; y <= location[players[turn.getTurn()].getLocation()].y && x >= location[players[turn.getTurn()].getLocation()].x; y+= 5,x-=8, i++){
            switch(i%2){
                case 0:
                    user_move[turn.getTurn()].move[0]->move(x,y);
                    user_move[turn.getTurn()].move[0]->repaint();
                    break;
                case 1:
                    user_move[turn.getTurn()].move[1]->move(x,y);
                    user_move[turn.getTurn()].move[1]->repaint();
            }
            Sleep(18);
        }
        user_move[turn.getTurn()].move[0]->close();
        user_move[turn.getTurn()].move[1]->close();
        user_move[turn.getTurn()].reverse_move[0]->show();
        user_move[turn.getTurn()].reverse_move[1]->show();
        if(players[turn.getTurn()].getLocation() <= 19 && players[turn.getTurn()].getLocation() >= 15){
            int tmp = location[now].x;
            int y = location[now].y;
            int xx = location[15].x;
            for(; tmp >= xx; xx+= 5, i++){
                switch(i%2){
                    case 0:
                        user_move[turn.getTurn()].reverse_move[0]->move(xx,y);
                        user_move[turn.getTurn()].reverse_move[0]->repaint();
                        break;
                    case 1:
                        user_move[turn.getTurn()].reverse_move[1]->move(xx,y);
                        user_move[turn.getTurn()].reverse_move[1]->repaint();
                }
                Sleep(18);
            }
        }
        if(players[turn.getTurn()].getLocation()>=26 && players[turn.getTurn()].getLocation() <= 28){
            for(; y <= location[players[turn.getTurn()].getLocation()].y && x <= location[players[turn.getTurn()].getLocation()].x; y+= 5,x+=8, i++){
                switch(i%2){
                    case 0:
                        user_move[turn.getTurn()].reverse_move[0]->move(x,y);
                        user_move[turn.getTurn()].reverse_move[0]->repaint();
                        break;
                    case 1:
                        user_move[turn.getTurn()].reverse_move[1]->move(x,y);
                        user_move[turn.getTurn()].reverse_move[1]->repaint();
                }
                Sleep(18);
            }
        }
        if(players[turn.getTurn()].getLocation() <= 4 && players[turn.getTurn()].getLocation() >=0){
            if(before_loc >=27 && before_loc <= 28){
                for(; y <= location[players[turn.getTurn()].getLocation()].y && x <= location[players[turn.getTurn()].getLocation()].x; y+= 5,x+=8, i++){
                    switch(i%2){
                        case 0:
                            user_move[turn.getTurn()].reverse_move[0]->move(x,y);
                            user_move[turn.getTurn()].reverse_move[0]->repaint();
                            break;
                        case 1:
                            user_move[turn.getTurn()].reverse_move[1]->move(x,y);
                            user_move[turn.getTurn()].reverse_move[1]->repaint();
                    }
                    Sleep(18);
                }
                user_move[turn.getTurn()].reverse_move[0]->close();
                user_move[turn.getTurn()].reverse_move[1]->close();
                user_move[turn.getTurn()].move[0]->show();
                user_move[turn.getTurn()].move[1]->show();
            }
            for(; y >= location[now].y; y-= 5, i++){
                switch(i%2){
                    case 0:
                        user_move[turn.getTurn()].move[0]->move(x,y);
                        user_move[turn.getTurn()].move[0]->repaint();
                        break;
                    case 1:
                        user_move[turn.getTurn()].move[1]->move(x,y);
                        user_move[turn.getTurn()].move[1]->repaint();
                }
                Sleep(11);
            }
        }
    }else if(before_loc == 10 || (before_loc >= 25 || before_loc <= 28)){
        user_move[turn.getTurn()].move[0]->close();
        user_move[turn.getTurn()].move[1]->close();
        user_move[turn.getTurn()].reverse_move[0]->show();
        user_move[turn.getTurn()].reverse_move[1]->show();
        for(; y <= location[players[turn.getTurn()].getLocation()].y && x <= location[players[turn.getTurn()].getLocation()].x; y+= 5,x+=8, i++){
            switch(i%2){
                case 0:
                    user_move[turn.getTurn()].reverse_move[0]->move(x,y);
                    user_move[turn.getTurn()].reverse_move[0]->repaint();
                    break;
                case 1:
                    user_move[turn.getTurn()].reverse_move[1]->move(x,y);
                    user_move[turn.getTurn()].reverse_move[1]->repaint();
            }
            Sleep(18);
        }
        user_move[turn.getTurn()].reverse_move[0]->close();
        user_move[turn.getTurn()].reverse_move[1]->close();
        user_move[turn.getTurn()].move[0]->show();
        user_move[turn.getTurn()].move[1]->show();
        if(players[turn.getTurn()].getLocation() <= 4 && players[turn.getTurn()].getLocation() >=0){
            for(; y >= location[now].y; y-= 5, i++){
                switch(i%2){
                    case 0:
                        user_move[turn.getTurn()].move[0]->move(x,y);
                        user_move[turn.getTurn()].move[0]->repaint();
                        break;
                    case 1:
                        user_move[turn.getTurn()].move[1]->move(x,y);
                        user_move[turn.getTurn()].move[1]->repaint();
                }
                Sleep(11);
            }
        }
    }
    user_move[turn.getTurn()].reverse_move[0]->close();
    user_move[turn.getTurn()].reverse_move[1]->close();
    user_move[turn.getTurn()].move[0]->close();
    user_move[turn.getTurn()].move[1]->close();
    update_statue(turn.getTurn());
}

void MainWindow::close_(){
    dice_->show();
    buy->close();
    lv1->close();
    lv2->close();
    lv3->close();
}

int MainWindow::moving(int dice){

    int index = turn.getTurn();
    int chk = 0;
    before_loc = players[index].getLocation();

    if(players[index].getLocation() == 5 && players[index].getIntersection() == 1){//첫번쨰 교차로에서 intersection == 1이면
        cout<<"FIRST CONER ENter"<<endl;
        chk = players[index].getLocation() + 14 + dice;
        if(chk > 24){//주사위가 6이 나오면
            chk -= 10;
        }
        players[index].setLocation(chk);
    }else if(players[index].getLocation() == 10 && players[index].getIntersection() == 1){//두번쨰 교차로에서 intersection == 1이면
        chk = players[index].getLocation() + 14 + dice;
        if(chk > 28){//주사위가 6이 나오면 && 한바퀴 돌면 돈과 카운트 ++
            players[index].countPlus();
            players[index].moneyPlus();
            chk = 0;
        }switch(dice){
        case 3:
            players[index].setLocation(22);
            break;
        case 4:
            players[index].setLocation(27);
            break;
        case 5:
            players[index].setLocation(28);
            break;
        default:
            players[index].setLocation(chk);
        }
    }else if(players[index].getLocation() == 22 && players[index].getIntersection() == 1){//세번쨰 교차로에서 intersection == 1이면
        chk = players[index].getLocation() + 4 + dice;
        if(chk > 28){//주사위가 6이 나오면 && 한바퀴 돌면 돈과 카운트 ++
            players[index].countPlus();
            players[index].moneyPlus();
            chk -= 29;
        }
        players[index].setLocation(chk);
    }else if(players[index].getLocation() >= 20 && players[index].getLocation() <= 24){//첫 번쨰 사선길이면
        chk = players[index].getLocation() + dice;
        if(chk == 30){//한바퀴 돌면 돈과 카운트 ++
            players[index].countPlus();
            players[index].moneyPlus();
            chk = 0;
        }if(chk > 24){//주사위가 6이 나오면
            chk -= 10;
        }
        players[index].setLocation(chk);
    }else if(players[index].getLocation() >= 25 && players[index].getLocation() <= 34){// 두번째 사선길
        static int aaa  = 0;
        chk = players[index].getLocation() + dice;
        if(chk > 29){//한바퀴 돌면 돈과 카운트 ++
            players[index].countPlus();
            players[index].moneyPlus();
            chk -= 29;
            players[index].setLocation(chk);
        }else if(chk == 29){
            if(players[index].getLocation() == 27){
                if(chk >= 29){//한바퀴 돌면 돈과 카운트 ++
                    players[index].countPlus();
                    players[index].moneyPlus();
                    chk -= 29;
                    players[index].setLocation(chk);
                }else{
                    players[index].setLocation(chk);
                }
            }else
                players[index].setLocation(28);
        }else if(chk == 28){
            if(aaa % 2 == 0)
                players[index].setLocation(27);
            else
                players[index].setLocation(chk);
        }else if(chk == 27){
            players[index].setLocation(22);\
        }else{
            players[index].setLocation(chk);
        }
    }else if(players[index].getLocation() >= 14 && players[index].getLocation() <= 19){//일반루트
        chk = players[index].getLocation() + dice;
        if(chk > 19){//한바퀴 돌면 돈과 카운트++
            players[index].countPlus();
            players[index].moneyPlus();
            chk -= 20;
        }
        players[index].setLocation(chk);
    }else{
        chk = players[index].getLocation() + dice;
        players[index].setLocation(chk);
    }
    moving_chk();
    cout<<"MOVING"<<endl;
    cout<<players[index].getLocation()<<",   "<<players[index].getIntersection()<<endl;
    return before_loc;
}
void MainWindow::moving_chk(){
    int index = turn.getTurn();

    if(players[index].getLocation()== 5){//첫번쨰
        cout<<"1 coner"<<endl;
        dice_->close();
        buy->close();
        lv1->close();
        lv2->close();
        lv3->close();
        switch(conerGame()){
            case -1:
                players[index].setIntersection(0);
                break;
            case 0:
                players[index].setIntersection(1);
                break;
            case 1:
                players[index].setIntersection(1);
                break;
            default:
             cout<<endl;
        }
    }else if(players[index].getLocation() == 10  && (players[index].getIntersection() != 1 || players[index].getIntersection() != 0) ){//두번째
        cout<<"2 coner"<<endl;
        dice_->close();
        buy->close();
        lv1->close();
        lv2->close();
        lv3->close();
        switch(conerGame()){
            case -1:
                players[index].setIntersection(0);
                break;
            case 0:
                players[index].setIntersection(1);
                break;
            case 1:
                players[index].setIntersection(1);
                break;
        }
    }else if(players[index].getLocation() == 15 && (players[index].getIntersection() != 1 || players[index].getIntersection() != 0) ){//세번쨰
        cout<<"3 coner"<<endl;
        dice_->close();
        buy->close();
        lv1->close();
        lv2->close();
        lv3->close();
        switch(conerGame()){
            case -1:
                players[index].setIntersection(-1);//무인도
                break;
            case 0:
                players[index].setIntersection(0);
                break;
            case 1:
                players[index].setIntersection(1);//세계여행
                break;
        }
        cout<<players[index].getIntersection()<<endl;
    }else if(((players[index].getLocation() == 22)&& (players[index].getIntersection() == 1 || players[index].getIntersection() == 0))){//중간
        cout<<"middle coner"<<endl;
        dice_->close();
        buy->close();
        lv1->close();
        lv2->close();
        lv3->close();
        switch(conerGame()){
            case -1:
                players[index].setIntersection(-1);
                break;
            case 0:
                players[index].setIntersection(0);//무인도
                break;
            case 1:
                players[index].setIntersection(1);//세계여행
                break;
        }
    }
}

void MainWindow::moving_image_load(){
    QString pwd[4], pwd_1[4][2], pwd_2[4][2];
    //정지
    pwd[0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\character_1.png";
    pwd[1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\character_2.png";
    pwd[2] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\character_3.png";
    pwd[3] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\character_4.png";

    //왼쪽바라보는
    pwd_1[0][0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_1_mv_4.png";
    pwd_1[0][1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_1_mv_3.png";
    pwd_1[1][0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_2_mv_4.png";
    pwd_1[1][1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_2_mv_3.png";
    pwd_1[2][0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_3_mv_4.png";
    pwd_1[2][1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_3_mv_3.png";
    pwd_1[3][0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_4_mv_4.png";
    pwd_1[3][1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_4_mv_3.png";
    //오른쪽 바라보는
    pwd_2[0][0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_1_mv_rv_4.png";
    pwd_2[0][1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_1_mv_rv_3.png";
    pwd_2[1][0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_2_mv_rv_4.png";
    pwd_2[1][1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_2_mv_rv_3.png";
    pwd_2[2][0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_3_mv_rv_4.png";
    pwd_2[2][1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_3_mv_rv_3.png";
    pwd_2[3][0] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_4_mv_rv_4.png";
    pwd_2[3][1] = "C:\\Users\\aolo26698824\\Desktop\\QT\\character\\ch_4_mv_rv_3.png";


    for(int i = 0; i < turn.getTurnSize(); i ++){
        user_move[i].stop = new QLabel();
        user_move[i].stop->setPixmap(QPixmap(pwd[i]));
        for(int k = 0; k < 2; k++){
            user_move[i].move[k] = new QLabel();
            user_move[i].move[k]->setPixmap(QPixmap(pwd_1[i][k]));
            user_move[i].reverse_move[k] = new QLabel();
            user_move[i].reverse_move[k]->setPixmap(QPixmap(pwd_2[i][k]));
        }
    }
}

void MainWindow::locateLevel(){
    for(int i = 0; i < level_size; i++){
        if(i != 0|| i!=5||i!=10||i!=15||i!=22){
        level[i].level1 = new QLabel(this);
        level[i].level1->setPixmap(QPixmap("C:\\Users\\aolo26698824\\Desktop\\QT\\low.png"));
        level[i].level1->setScaledContents(true);
        level[i].level1->setParent(startWidget);
        level[i].level2 = new QLabel(this);
        level[i].level2->setPixmap(QPixmap("C:\\Users\\aolo26698824\\Desktop\\QT\\middle.png"));
        level[i].level2->setScaledContents(true);
        level[i].level2->setParent(startWidget);
        level[i].level3 = new QLabel(this);
        level[i].level3->setPixmap(QPixmap("C:\\Users\\aolo26698824\\Desktop\\QT\\high.png"));
        level[i].level3->setScaledContents(true);
        level[i].level3->setParent(startWidget);
        level[i].flag[0] = new QLabel(this);
        level[i].flag[0]->setPixmap(QPixmap("C:\\Users\\aolo26698824\\Desktop\\QT\\flag_1.png"));
        level[i].flag[0]->setScaledContents(true);
        level[i].flag[0]->setParent(startWidget);
        level[i].flag[1] = new QLabel(this);
        level[i].flag[1]->setPixmap(QPixmap("C:\\Users\\aolo26698824\\Desktop\\QT\\flag_2.png"));
        level[i].flag[1]->setScaledContents(true);
        level[i].flag[1]->setParent(startWidget);
        level[i].flag[2] = new QLabel(this);
        level[i].flag[2]->setPixmap(QPixmap("C:\\Users\\aolo26698824\\Desktop\\QT\\flag_3.png"));
        level[i].flag[2]->setScaledContents(true);
        level[i].flag[2]->setParent(startWidget);
        level[i].flag[3] = new QLabel(this);
        level[i].flag[3]->setPixmap(QPixmap("C:\\Users\\aolo26698824\\Desktop\\QT\\flag_4.png"));
        level[i].flag[3]->setScaledContents(true);
        level[i].flag[3]->setParent(startWidget);
        }
    }
    level[29].flag[0]->setPixmap(QPixmap("C:\\Users\\aolo26698824\\Desktop\\QT\\flag.png"));
    location[0].x = 1150; location[0].y = 595;
    for(int i = 1; i <5; i++){

        location[i].x = 1150;location[i].y = 495-(100*(i-1));
        level[i].level1->move(1140,450-(100*(i-1)));
        level[i].level2->move(1140,450-(100*(i-1)));
        level[i].level3->move(1140,450-(100*(i-1)));
        for(int k = 0; k <4; k++)
        level[i].flag[k]->move(1205,450-(100*(i-1)));
    }
    location[5].x = 1150; location[5].y = 95;
    for(int i = 6; i < 10; i++){
        location[i].x = 1010-(150*(i-6));location[i].y = 95;
        level[i].level1->move(1000-(150 * (i-6)),50);
        level[i].level2->move(1000-(150 * (i-6)),50);
        level[i].level3->move(1000-(150 * (i-6)),50);
        for(int k = 0; k <4; k++)
           level[i].flag[k]->move(1065-(147 * (i-6)),50);
    }
    location[10].x = 410; location[10].y = 95;

    for(int i = 11; i <15; i++){
        location[i].x = 410; location[i].y = 195+(100*(i-11));
        level[i].level1->move(400,150+(100*(i-11)));
        level[i].level2->move(400,150+(100*(i-11)));
        level[i].level3->move(400,150+(100*(i-11)));
        for(int k = 0; k <4; k++)
            level[i].flag[k]->move(475,150+(100*(i-11)));
    }
    location[15].x = 410; location[15].y = 550;

    for(int i = 16; i < 20; i++){
        location[i].x = 560+(150 * (i-16)); location[i].y = 595;
        level[i].level1->move(550+(150 * (i-16)),550);
        level[i].level2->move(550+(150 * (i-16)),550);
        level[i].level3->move(550+(150 * (i-16)),550);
        for(int k = 0; k <4; k++)
        level[i].flag[k]->move(625+(145 * (i-16)),550);
    }
    location[20].x = 1010; location[20].y = 195;
    level[20].level1->move(1000,150);
    level[20].level2->move(1000,150);
    level[20].level3->move(1000,150);
    for(int k = 0; k <4; k++)
        level[20].flag[k]->move(1070,150);

    location[21].x = 900; location[21].y = 280;
    level[21].level1->move(890,235);
    level[21].level2->move(890,235);
    level[21].level3->move(890,235);
    for(int k = 0; k <4; k++)
        level[21].flag[k]->move(955,235);

    location[22].x = 770; location[22].y = 310;
    location[23].x = 670; location[23].y = 410;
    level[23].level1->move(660,370);
    level[23].level2->move(670,370);
    level[23].level3->move(660,370);
    for(int k = 0; k <4; k++)
        level[23].flag[k]->move(730,370);

    location[24].x = 560; location[24].y = 495;
    level[24].level1->move(550,450);
    level[24].level2->move(550,450);
    level[24].level3->move(550,450);
    for(int k = 0; k <4; k++)
        level[24].flag[k]->move(620,450);

    location[25].x = 550; location[25].y = 195;
    level[25].level1->move(540,150);
    level[25].level2->move(540,150);
    level[25].level3->move(540,150);
    for(int k = 0; k <4; k++)
        level[25].flag[k]->move(610,150);

    location[26].x = 670; location[26].y = 265;
    level[26].level1->move(660,220);
    level[26].level2->move(660,220);
    level[26].level3->move(660,220);
    for(int k = 0; k <4; k++)
        level[26].flag[k]->move(730,220);

    location[27].x = 895; location[27].y = 415;
    level[27].level1->move(885,370);
    level[27].level2->move(885,370);
    level[27].level3->move(885,370);
    for(int k = 0; k <4; k++)
        level[27].flag[k]->move(955,370);

    location[28].x = 1010; location[28].y = 495;
    level[28].level1->move(1000,450);
    level[28].level2->move(1000,450);
    level[28].level3->move(1000,450);
    for(int k = 0; k <4; k++)
        level[28].flag[k]->move(1065,450);

    for(int i = 0; i <level_size; i++){
        level[i].level1->close();
        level[i].level2->close();
        level[i].level3->close();
        level[i].flag[0]->close();
        level[i].flag[1]->close();
        level[i].flag[2]->close();
        level[i].flag[3]->close();
    }

}


void insertMapInfo(string user[]){
    Language tmp;

    tmp.Add(2,0,"Start",user[0],user[1],user[2],user[3]," ",0,0,0,0,0,0,0,0,0);
    mmap.push_back(tmp);
    tmp.Add(0,1,"Hope", " ", " ", " ", " ", " ", 0, 8, 5, 15, 25, 2, 6, 18, 45);
    mmap.push_back(tmp);
    tmp.Add(0,2,"Scala", " ", " ", " ", " ", " ", 0, 8, 5, 15, 25, 2, 6, 18, 45);
    mmap.push_back(tmp);
    tmp.Add(0,3,"Simula67", " ", " ", " ", " ", " ", 0,10, 5, 15, 25, 3, 9, 27, 55);
    mmap.push_back(tmp);
    tmp.Add(0,4,"Ada", " ", " ", " ", " ", " ", 0, 18, 10, 30, 50, 7, 20, 55, 95);
    mmap.push_back(tmp);

    tmp.Add(2,5,"First-Coner", " ", " ", " ", " ", " ", 0, 0, 0, 0, 0, 0, 0, 0, 0);
    mmap.push_back(tmp);
    tmp.Add(0,6,"B", " ", " ", " ", " ", " ", 0, 8, 5, 15, 25, 2, 6, 18, 45);
    mmap.push_back(tmp);
    tmp.Add(0,7,"C", " ", " ", " ", " ", " ", 0, 28, 15, 45, 75, 12, 36, 85, 120);
    mmap.push_back(tmp);
    tmp.Add(0,8,"Mercury", " ", " ", " ", " ", " ", 0, 5, 5, 15, 25, 1, 3, 9, 25);
    mmap.push_back(tmp);
    tmp.Add(0,9,"Prolog", " ", " ", " ", " ", " ", 0, 14, 10, 30, 50, 5, 15, 45, 75);
    mmap.push_back(tmp);

    tmp.Add(2,10,"Second-Coner", " ", " ", " ", " ", " ", 0, 0, 0, 0, 0, 0, 0, 0, 0);
    mmap.push_back(tmp);
    tmp.Add(0,11,"F#", " ", " ", " ", " ", " ", 0, 16, 10, 30, 50, 6, 18, 50, 90);
    mmap.push_back(tmp);
    tmp.Add(1,12,"Haskell",  " ", " ", " ", " ", " ", 0, 20, 0, 0, 0,30, 0, 0, 0);
    mmap.push_back(tmp);
    tmp.Add(0,13,"Perl",  " ", " ", " ", " ", " ", 0, 10, 5, 15, 25, 3, 9, 27, 55);
    mmap.push_back(tmp);
    tmp.Add(0,14,"Objectiv-C", " ", " ", " ", " ", " ", 0, 20, 10, 30, 50, 8, 22, 60, 100);
    mmap.push_back(tmp);

    tmp.Add(2,15,"Third-Coner", " ", " ", " ", " ", " ", 0, 0, 0, 0, 0, 0, 0, 0, 0);
    mmap.push_back(tmp);
    tmp.Add(0,16,"Delphi", " ", " ", " ", " ", " ", 0, 16, 10, 30, 50, 6, 18, 50, 90);
    mmap.push_back(tmp);
    tmp.Add(1,17,"ASP", " ", " ", " ", " ", " ", 0, 20, 0, 0, 0,30, 0, 0, 0);
    mmap.push_back(tmp);
    tmp.Add(0,18,"C#",  " ", " ", " ", " ", " ", 0, 30, 20, 60, 100, 13, 39, 90, 127);
    mmap.push_back(tmp);
    tmp.Add(0,19,"C++",  " ", " ", " ", " ", " ", 0, 32, 20, 60, 100, 15, 45, 100, 140);
    mmap.push_back(tmp);

    tmp.Add(0,20,"ALGOL",  " ", " ", " ", " ", " ", 0, 18, 10, 30, 50, 7, 20, 55, 95);
    mmap.push_back(tmp);
    tmp.Add(0,21,"Pascal",  " ", " ", " ", " ", " ", 0, 22, 15, 45, 75, 10, 30, 75, 110);
    mmap.push_back(tmp);
    tmp.Add(2,22,"Middel-Coner", " ", " ", " ", " ", " ", 0, 0, 0, 0, 0, 0, 0, 0, 0);
    mmap.push_back(tmp);
    tmp.Add(1,23,"CSS", " ", " ", " ", " ", " ", 0, 50, 0, 0, 0,60, 0, 0, 0);
    mmap.push_back(tmp);
    tmp.Add(0,24,"Python",  " ", " ", " ", " ", " ", 0, 24, 15, 45, 75, 10, 30, 75, 110);
    mmap.push_back(tmp);

    tmp.Add(0,25,"PHP", " ", " ", " ", " ", " ", 0, 26, 15, 45, 75, 11, 33, 80, 115);
    mmap.push_back(tmp);
    tmp.Add(0,26,"Ruby", " ", " ", " ", " ", " ", 0, 32, 20, 60, 100, 15, 45, 100, 140);
    mmap.push_back(tmp);
    tmp.Add(0,27,"Java",  " ", " ", " ", " ", " ", 0, 35, 20, 60, 100, 17, 50, 110, 150);
    mmap.push_back(tmp);
    tmp.Add(0,28,"JavaScript",  " ", " ", " ", " ", " ", 0, 40, 30, 75, 120, 25, 70, 140, 200);
    mmap.push_back(tmp);

}
MainWindow::~MainWindow()
{

}
