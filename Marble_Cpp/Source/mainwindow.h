#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include<QImage>
#include<QPixmap>
#include<QLabel>
#include<QMessageBox>
#include<iostream>
#include<QDesktopWidget>
#include<QApplication>
#include<cstdlib>
#include<time.h>
#include<QtCore/QCoreApplication>
#include<QCoreApplication>
#include<QGraphicsScene>
#include<QGraphicsItem>
#include<QGraphicsView>
#include<Windows.h>
#include<QVector>
#include<QPushButton>
#include<QVBoxLayout>
#include<QPushButton>
#include<QCheckBox>
#include<QButtonGroup>
#include<QTimer>
#include<QThread>
#include<QMovie>
using namespace std;



//***********GAME CLASS************

class special{//Language에 대한 부가정보(유저)
protected:
      string play[4];//땅에 어떤 플레이어가 있는지 나타낼 변수
public:
      void setUser(string user, int index){ play[index] = user; }
      string getUser(int index){ return play[index];}
};
class Level{
    public:QLabel *level1, *level2, *level3;
    public:QLabel *flag[4];
};
class Language : public special{//언어 클래스(땅)
protected:
      string name;//
      string owner;//언어 주인
      int level;//언어 레벨
      int price[4];//가격
      int hacking[4];//통행료
      int hackingIndex;
      int noUpgrade;
      int loc;
public:
      Language(){}
      void Add(int noUpgrade = 0, int loc = 0, string name = NULL, string p1 = NULL, string p2 = NULL, string p3 = NULL,
               string p4 = NULL, string owner = NULL, int level = 0, int languagePrice = 0,
               int lowLevel = 0, int middleLevel = 0, int highLevel = 0, int languagePay = 0,
               int lowPay = 0, int middlePay = 0, int highPay = 0){


        this->noUpgrade = noUpgrade;
        this->loc = loc;
        this->name = name;
        play[0] = p1;
        play[1] = p2;
        play[2] = p3;
        play[3] = p4;
        this->owner =owner;
        this->level = level;
        price[0] = languagePrice;
        price[1] = lowLevel;
        price[2] = middleLevel;
        price[3] = highLevel;
        hacking[0] = languagePay;
        hacking[1] = lowPay;
        hacking[2] = middlePay;
        hacking[3] = highPay;
        hackingIndex = 0;
      } //맵에대한 정보

      void setOwner(string owner){this->owner = owner;level = 0;}//소유자 등록
      void initOwner(){this->owner =" ";}
      void upHacking(){hackingIndex++;}//통행료 증가
      void upLevel(){level++;}//레벨증가
      string getName() { return name;}
      string getOwner(){return owner;}
      int getPrice(){return price[level];}
      int getPrice(int n){return price[n];}
      int getHacking(){return hacking[hackingIndex];}
      int getLevel(){return level;}
      int getNoupgrade(){return noUpgrade;}
      int getLocation(){return loc;}
};

class Player{
private:
      string name;
      int money;
      int location;
      int property;
      int goldCard;//황금카드 번호
      int intersection;
      int count;
      int saveLoc;
      bool death;
      bool death_chk;
public:
      Player(){intersection = -2; goldCard = -1; count = 0; money = 200; death = false; death_chk = false; property = 0; saveLoc = -1;}
      void setPlayer(int money, int location){ this->money = money; this->location = location; }//플레이어 정보 저장
      void setLocation(int loc){location = loc;}//플레이어 위치변경
      void setGoldCard(int num){ goldCard = num;}
      void M_Money(int mon){money -= mon;}//플레이어 돈 변경
      void P_Money(int mon){money += mon;}
      void setIntersection(int in){ intersection = in;}
      void setName(string name){ this->name = name;}
      void moneyPlus(){money += 30;}
      void countPlus(){count++;}
      void setSaveLoc(int n){saveLoc = n;}
      string getName(){return name;}
      int getGoldCard(){return goldCard;}
      int getSaveLoc(){return saveLoc;}
      int getIntersection(){return intersection;}
      int getLocation(){return location;}//플레이어 위치리턴
      int getMoney(){return money;}//플레이어 돈 리턴
      int getCount(){return count;}//몇 바퀴를 돌았는가
      bool getDeath(){return death;}
      void setDeath(){death = true;}
      void setProperty(int pro){ this->property += pro;}
      int getProperty(){ return property;}
      void setDeathCheck(){death_chk = true;}
      int getDeathCheck(){return death_chk;}

      vector<Language> mine;
};
class Game{
private:
      int turn;
      int turn_size;
      int arr_turn[4];//차례
      int i = 0;
      int origin;
      int k = 0;
public:/*

배열 이용해서 파산한놈 인덱스는 뺴고 턴 계속 넘기는거 하자!

*/
      Game(){
          arr_turn[0] = 0;
          arr_turn[1] = 1;
          arr_turn[2] = 2;
          arr_turn[3] = 3;
      }//턴
      Game(int size){
          origin = size;
          turn_size = size;
      }

      void nextTurn(){
          i++;
          i = i % turn_size;
          k = i;
      }//턴 셋팅
      int getNextTurn(){
          k;
          if(arr_turn[++k % turn_size] != -1)
            return arr_turn[k % turn_size];
          else
              getNextTurn();
      }

      int getTurn(){
          if(arr_turn[i] != -1)
            return arr_turn[i];
          else{
              cout<<"Getturn next "<<endl;
              nextTurn();
              getTurn();
          }
      }
      int getBeforeTurn(){
          if((i) == 0){
              return arr_turn[turn_size-1];
          }
          if((i-1) != -1 ){
              if(arr_turn[i-1] != -1){
                return arr_turn[i-1];
              }else{
                  cout<<"Before Turn Error"<<endl;
              }
          }else{
              if(arr_turn[turn_size] != -1){
                return arr_turn[turn_size];
              }else{
                  cout<<"Before Turn Error22222"<<endl;
              }
          }
      }

      int getTurn(int a){
          return arr_turn[a];
      }

      //int conerGame();//각 코너진입시 게임실행
      int getOrigin(){return origin;}
      void setTurnSize(int k){turn_size = k;}
      int getTurnSize(){return turn_size;}
      void MMturn(int k){
               for(int a = 0; a < turn_size; a++){
                  if(arr_turn[a] == k){
                      arr_turn[a] = -1;
                      break;
                  }
              }

      }
};


class X_Y{
 public:
    int x;
    int y;
};
class Turn_image{
public:
    QLabel *turn[4];
};

class Image_move{
public:
    QLabel *stop;
    QLabel *move[2];
    QLabel *reverse_move[2];
};

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:

    int ddd = 0;
    QLabel *label, *print_label, *statues[4], *money[4], *property[4], *gdCard[4];
    QLabel *SRP[3], *ppp,*winer;
    QPixmap *level1, *level2, *level3;
    QPushButton *dice_ , *dice_2, *dice_3;
    QPushButton *p1,*p2,*p3,*p4;
    QPushButton *buy,*lv1, *lv2, *lv3, *close, *back;
    QPushButton *rock, *paper, *scissors, *exit, *wdl, *wdll, *enter, *inst;
    QWidget *hello,*First,*startWidget, *Coner, *Coner_,*Coner__, *Instruction;
    QMovie *movie;
    int height;
    int width;
    void imgload();
    QVBoxLayout *vboxLayout;
    QTimer *countdown;
    MainWindow(QWidget *parent = 0);

        ~MainWindow();
    void start();


private slots:
    void goldCard();
    void instruction();
    void gotoInst();
    void gotochoose();
    void usingGdCard();
    void usingGdCard_();
    int conerGame();
    void gameExit();
    void gameExit_();
    void gameExit__();
    void scissors_();
    void rock_();
    void paper_();
    void chk_win();
    void chk_win_();
    void DiceStart();
    void join4();
    void join3();
    void join2();
    void join1();
    void join();
    void locateLevel();
    void SStart();
    void moving_image_load();
    void moving_chk();
    int moving(int dice);
    void update_image();
    void menu();
    void buy_();
    void buy__();
    void close_();
    void lv_1();
    void lv_2();
    void lv_3();
    void lv__1();
    void lv__2();
    void lv__3();
    void statue();
    void mainWin();
    void update_statue(int index);
};
#endif // MAINWINDOW_H
