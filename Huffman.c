#include<stdio.h>
#include<string.h>
#include<stdlib.h>

struct huffTable{
	char ch;
	int count;
	char code[20];
	unsigned char binary;
	double frequency;
}HT[128];

struct compression{
	char ch;
	char code[20];
	int len;
}CH[128];

typedef struct Tree{
	double val;
	char ch;
	char dad_val;
	struct Tree *left, *right, *dad;
}Tree;
typedef struct min{
	int index;
	float val;
}min;

int exception[100], exception_count = 0, com_len = 0;
int HT_SIZE = 0, CH_SIZE = 0;

void init(){
	for(int i = 0; i < 128; i ++){
		HT[i].ch ='\0';
		HT[i].count = 0;
		HT[i].binary = 0;
		CH[i].ch = '\0';
		CH[i].len = 0;
		for(int a = 0; a < 20; a ++){
			HT[i].code[a] = '\0';
			CH[i].code[a] = '\0';
		}
	}
}
int chk_HT(char c){
	for(int i = 0; i < HT_SIZE; i ++){
		if(HT[i].ch == c)
			return i;
	}
	return -1;
}
void sort_(){
	struct huffTable tmp ;
	for(int i = 0; i < HT_SIZE-1; i++){
		for(int j = i+1; j < HT_SIZE; j++)
			if(HT[i].count > HT[j].count){
				tmp = HT[j];
				HT[j] = HT[i];
				HT[i] = tmp;
			}
	}
}
int chk_exception(int index){
	for(int z = 0; z < exception_count; z++){
		if(index == exception[z])
			return -1;
	}
	return 0;
}
unsigned char pack_cd(const char *buff){
	unsigned char res = 0;
	for(int i = strlen(buff)-1; i>=0; i--){
		res |= (*buff - '0')<<i;
		buff++;
	}
	return res;
}
unsigned char pack(const char *buf){
	unsigned char res = 0;
	for(int i = 7 ; i >= 0; i--){
		if( *buf == '1')
			res = res | (1<<i);
		buf++;
	}
	return res;
}

void out(char *buf, FILE *f2){
	int str_len = strlen(buf);
	char padding_len = (8 - (str_len % 8)) % 8;
	char tmp_padding = padding_len;
	char padding_binary[8]="0", padding_reverse[8] = "0";
	//printf("code :%s  padding :%d  ",buf,padding_len);
	for(int i = 0; i < padding_len; i++)
		strcat(buf,"0");

	for(int i = 0; tmp_padding != 0; i++){
		if(tmp_padding % 2 == 1)
			padding_binary[i] = '1';
		else
			padding_binary[i] = '0';

		tmp_padding /= 2;

		if(tmp_padding == 1){
			padding_binary[i+1] = '1';
			padding_binary[i+2] = '\0';
			tmp_padding--;
			break;
		}
	}
	for(int i = 0; padding_binary[i] != '\0'; i++){
		padding_reverse[strlen(padding_binary) - i -1] = padding_binary[i];
	}
	char padding_write = pack_cd(padding_reverse);
	fputc(padding_write,f2);
	com_len++;
	char *buf_ptr = buf;
	for(int i = 0; i * 8 < strlen(buf); i++){
		unsigned char byte = pack(buf_ptr);
		fputc(byte, f2);
		buf_ptr += 8;
		com_len++;
	}
}
int chk_FF(char * buf){
	int cnt = 0;
	for(int i = 0; i < 8; i ++){
		if(buf[i] == '1')
			cnt++;
	}
	if(cnt == 8)
		return 1;
	return -1;
}
int binary_To_char(char *buff){
	int res = 0;
	int i;
	for(i = 7; i>=0; i--){
		res |= (*buff - '0') << i;
		buff++;
	}
	return res;
}
void mk(){
	int chk_rt = -1;
	int rt = HT_SIZE;
	min min[2];
	int size = ((HT_SIZE * 2) - 1);
	int break_chk = -1;
	char code_tmp[20];
	Tree node[size];

	for(int i = 0; i < size; i++){
		node[i].val = HT[i].frequency;
		node[i].ch = HT[i].ch;
		node[i].left = NULL;
		node[i].right = NULL;
		node[i].dad = NULL;
		node[i].dad_val = '\0';
	}
	fflush(stdin);
	if(node[rt].left == NULL && node[rt].right == NULL){
		node[rt].left = &node[0];
		node[rt].right = &node[1];
		node[rt].val = node[0].val + node[1].val;
		node[0].dad = &node[rt];
		node[1].dad = &node[rt];
		node[0].dad_val = '0';
		node[1].dad_val = '1';
		printf("\nFIRST RT ! \nch = %c val = %f  left char :  %c  %f code : %c right char :  %c  %f code : %c\n",node[rt].ch,node[rt].val,node[rt].left->ch,node[rt].left->val, node[rt].left->dad_val,node[rt].right->ch,node[rt].right->val,node[rt].right->dad_val);
		exception[exception_count++] = 0;
		exception[exception_count++] = 1;
		rt++;
	}


	for(int i = 1; i < (size- HT_SIZE)  ; i++){
		int min_count = 0;
		fflush(stdin);

		for(int i = 0; i <2; i++){
			min[i].val = 1;
			min[i].index = -1;
		}

		for(int x = 0; x < 2; x++){
			for(int z = 0; z < rt; z++){
				if(chk_exception(z) == -1)
					continue;
				if(min[0].index == z)
					continue;

				if(min[min_count].val > node[z].val){
					min[min_count].index = z;
					min[min_count].val = node[z].val;
				}
			}min_count = 1;
		}
		exception[exception_count++] = min[0].index;
		exception[exception_count++] = min[1].index;

		node[rt].val = node[min[0].index].val + node[min[1].index].val;
		node[rt].left = &node[min[0].index];
		node[rt].right = &node[min[1].index];
		node[min[0].index].dad = &node[rt];
		node[min[1].index].dad = &node[rt];
		node[min[0].index].dad_val = '0';
		node[min[1].index].dad_val = '1';
		printf("\n%d RT ! \nch = %c val = %f  left  char : %c  %f code : %c right  char : %c  %f code : %c\n",i+1,node[rt].ch,node[rt].val,node[rt].left->ch,node[rt].left->val, node[rt].left->dad_val,node[rt].right->ch,node[rt].right->val,node[rt].right->dad_val);
		rt++;

		fflush(stdin);
	}
	fflush(stdin);
	fflush(stdin);
	fflush(stdin);

	for(int g = 0; g < HT_SIZE; g++){
		for(int z = 0; z < 20; z ++){
			code_tmp[z] = '\0';
		}
		for(int index = 0; node[g].dad_val != '\0'; index++){
			code_tmp[index] = node[g].dad_val;
			node[g] = *node[g].dad;
		}
		for(int z = 0; z <= strlen(code_tmp); z++){
			HT[g].code[z] = code_tmp[strlen(code_tmp) -1 - z];
		}
  		HT[g].code[strlen(HT[g].code)-1] = '\0';
		fflush(stdin);
	}


}/*
void file_writing(char *buf, FILE *f4){
	printf(" strlen : %d",strlen(buf));
	int buf_fp = 0;
	int save = 0;
	int count = 0;
	int flag = 0;
	printf("\nWIRTE  %s  CH SIZE :%d ",buf,CH_SIZE);
	while(1){
		for(int i = 0; i < CH_SIZE; i++){
			for(int k = 0; k < CH[i].len; k++){
				if(CH[i].code[k] == buf[buf_fp++]){
					count++;
				}
			}
			if(count == CH[i].len){
				printf("\ninput :%s (buf : %d, save : %d) -> %c",CH[i].code,buf_fp, save,CH[i].ch);
				save = buf_fp;
				fputc(CH[i].ch,f4);
				flag = 1;
			}
			if(flag == 1){
				buf_fp = save;
				count = 0;
			}else{
				buf_fp = 0;
				count = 0;
			}
		}
		if(save == strlen(buf))
			break;
	}

}
void file_w(char *buf, FILE *f4){
	int loc = 0;
	int count = 0;
	int save = 0;
	int flag = 0;
	for(;;){
		for(int i = 0; i < CH_SIZE; i++){
			for(int a = 0, loc = save; CH[i].code[a] != '\0'; i++, loc++){
				if(buf[loc] == CH[i].code[a]){
					count++;
				}
			}
			if(count == CH[i].len){
				fputc(CH[i].ch,f4);
				int flag = 1;
			}
			if(flag == 1){
				save = loc;
			}else{
				loc = 0;
			}
			count = 0;
		}
		if(save == strlen(buf))
			break;
	}
}*/
int main(int ar, char *argv[]){
	int len, choose, totla_len = 0,chk_0 = 0, chk_ht_index = -1, ff_flag = 0, i,k,g,jump_flag;
	int location = 0, location2 = 0;
	char before_original[16];
	int origin_index = 0, origin_index2 = 0;
	int padding = 0;
	float fre = 0;
	char text[200];
	char before_read_data[24];
	char read_data[24];
	char tmp[16], tmptmp[8];
	char start[8] ="11111111";
	char file_name[20];
	char *file = file_name;
	char read;
	char read_arr[8], G[20], F[20];
	char mask[8] = {0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};
	int result = 0;
	static int first = 0;
	Tree *Tre = NULL;
	init();
	FILE *f;
	FILE *f1;
	FILE *f2;
	FILE *f3;
	FILE *f4;

	for(;;){
		fflush(stdin);
		printf("what do u want to do?\n");
		printf("1. compression\n");
		printf("2. cancellation of compression(~.bin)\n");
		printf("3. exit\n");
		scanf("%d",&choose);
		if(choose == 3)
			break;
		switch(choose){
			case 1:
				printf("write textFile(~.txt) : ");
				scanf("%s",G);
				fflush(stdin);
				printf("\nwrite output file..(~.bin)  : ");
				scanf("%s",F);
				fflush(stdin);
				f2 = fopen(F,"wb");
				char tmp[16];
				f  = fopen(G,"r");
				f1 = fopen(G,"r");
				if(f == NULL || f1 == NULL)
					printf("f file open error");
				while(fgets(text,200,f) != NULL){
					len = strlen(text);
					totla_len += len;
					if(HT[0].ch == '\0'){
						HT[0].ch = text[0];
						HT[0].count++;
						HT_SIZE++;
						chk_0 = -1;
					}
					for(int i = 0; i < len; i ++){
						if(chk_0 == -1){
							chk_0++;
							continue;
						}
						int chk = 0;
						chk = chk_HT(text[i]);
						if(chk != -1){
							HT[chk].count++;
						}else{
							HT[HT_SIZE].ch = text[i];
							HT[HT_SIZE].count++;
							HT_SIZE++;
						}
					}

				}
				printf("TOTAL LEN : %d\n",totla_len);
				for(int i = 0; i <HT_SIZE; i++){
					fre += HT[i].frequency = ((float)HT[i].count / (float)(totla_len));
				}
				sort_();
				mk();
				printf("\n******************HUFFMAN TABLE******************");
				printf("\nchar  (ascii)\tcount\tFrequency\tCode\n");
				printf("==================================================\n");
				for(int i = 0; i < HT_SIZE; i++){
					printf("%c       (%d)\t%d\t%f\t%s\t\n",HT[i].ch,HT[i].ch,HT[i].count,HT[i].frequency,HT[i].code);
				}

				for(int h = 0; h < HT_SIZE; h++){
					HT[h].binary = pack_cd(HT[h].code);
					strcpy(tmp,HT[h].code);
					//printf("%s\n",tmp);
					if(strlen(HT[h].code) > 8){
						fputc(pack(start),f2);
						com_len++;
					}
					fputc(HT[h].ch,f2);
					com_len++;
					out(tmp,f2);
					//printf("(%s,%d) ",HT[h].code,HT[h].binary);
				}
				printf("\n\n");

				char buf[40];
				int buf_loc = 0;
				fputc(pack(start),f2);
				fputc(pack(start),f2);
				com_len += 2;
				while(fgets(text,200,f1) != NULL){
					len = strlen(text);
					for(int z = 0; z < len; z++){
						chk_ht_index = chk_HT(text[z]);
						if(chk_ht_index != -1){
							if(buf_loc + strlen(HT[chk_ht_index].code) <= 24){
								for(int v = 0; HT[chk_ht_index].code[v] != '\0'; v++){
									buf[buf_loc++] = HT[chk_ht_index].code[v];
								}
							}else{
								buf_loc = 0;
								//printf("buf : %s",buf);
								out(buf,f2);
								for(int b = 0; b < 40; b++)
									buf[b] = '\0';
								for(int v = 0; HT[chk_ht_index].code[v] != '\0'; v++)
									buf[buf_loc++] = HT[chk_ht_index].code[v];
							}
						}
					}

				}
				printf("frequence : %f \n",fre);
				printf("HT_SIZE  : %d\n",HT_SIZE);
				printf("Compression rate -> %f END\n",(float)com_len/(float)totla_len);
				break;
				case 2:
					printf("\n write file name : ");
					scanf("%s",file);
					fflush(stdin);
					f3 = fopen(file,"rb");

					printf("\nWirte output text file name : ");
					scanf("%s",file);
					fflush(stdin);
					f4 = fopen(file,"w");

					if(f3 == NULL || f4 == NULL)
						printf("binary file open error\n");

					while(fread(&read, sizeof(read),1,f3)){
						for( i = 0 ; i < 8; i ++){
							result = mask[i] & read;
							if(result){
								tmptmp[i] = '1';
							}else{
								tmptmp[i] = '0';
							}
						}

						for(i = 0; i < 8; i++){
							read_arr[i] = '\0';
						}

						for(i = 0; i < 8; i++){
							read_arr[i] = tmptmp[i];
						}
						jump_flag = 0;
						if(chk_FF(read_arr) == 1){
							ff_flag++;
							jump_flag = 1;
						}

						if(ff_flag >= 2){
							first++;
							if(first != 1){
								switch(location){
									case 0:
										padding = binary_To_char(read_arr);
										break;
									case 1: case 2: case 3:
										for( k = 0 ; k < 8; k++){
											before_read_data[origin_index++] = read_arr[k];
										}
								}location++;

								if(location == 4){
									for( i = 0; i < origin_index - padding; i++)
										read_data[i] = before_read_data[i];
									//printf("\ncode : %s ",read_data);

									int buf_fp = 0;
									int save = 0;
									int count = 0;
									int flag = 0;

									while(1){
										for(int i = 0; i < CH_SIZE; i++){
											for(int k = 0; k < CH[i].len; k++){
												if(CH[i].code[k] == read_data[buf_fp++]){
													count++;
												}
											}
											if(count == CH[i].len){
												printf("\ninput :%s (buf : %d, save : %d) -> %c",CH[i].code,buf_fp, save,CH[i].ch);
												save = buf_fp;
												fputc(CH[i].ch,f4);
												flag = 1;
											}
											if(flag == 1){
												buf_fp = save;
												count = 0;
											}else{
												buf_fp = 0;
												count = 0;
											}
										}
										if(save == strlen(read_data)){
											//printf("  %d vs %d  ",save, strlen(read_data));
											break;
										}
									}



									for( i = 0; i <24; i++){
										read_data[i] = '\0';
									}
									padding = 0;
									location = 0;
									origin_index = 0;
								}
							}
						}else{// TABLE READ
							if(jump_flag != 1){
								if(ff_flag == 1 ){
									switch(location){
										case 0:
											CH[CH_SIZE].ch = binary_To_char(read_arr);
											break;
										case 1:
											padding = binary_To_char(read_arr);
											break;
										case 2:
											for( k = 0 ; k < 8; k++){
												before_original[origin_index++] = read_arr[k];
												}
											break;
										case 3:
											for( k = 0; k < 8; k++){
												before_original[origin_index++] = read_arr[k];
											}
											break;
									}location++;
									if(location == 4){
										for( g = 0; g < origin_index - padding; g++){
											CH[CH_SIZE].code[g] = before_original[g];
										}
										CH[CH_SIZE].len = strlen(CH[CH_SIZE].code);
										CH_SIZE++;
										ff_flag = 0;
										location = 0;
										origin_index = 0;
									}
								}else if(ff_flag != 1){
									switch(location2){
										case 0:
											CH[CH_SIZE].ch = binary_To_char(read_arr);
											break;
										case 1:
											padding = binary_To_char(read_arr);
											break;
										case 2:
											for( k = 0 ; k < 8; k++){
												before_original[origin_index2++] =  read_arr[k];
											}
											break;
									}
									location2++;
									if(location2 > 2){
										for( g = 0; g < origin_index2 - padding; g++){
											CH[CH_SIZE].code[g] = before_original[g];
										}
										CH[CH_SIZE].len = strlen(CH[CH_SIZE].code);
										CH_SIZE++;
										location2 = 0;
										origin_index2 = 0;
									}
								}
							}
						}
					}
					printf("\n\n============Readed Huffman Table============\n");
					printf("--------------------------------------------\n");
					printf("Num\tchar\tcode\t\tlen\n");
					for( i = 0; i < CH_SIZE; i++)
						printf("%d\t%c\t%s\t\t%d\n",i+1, CH[i].ch, CH[i].code, CH[i].len);
					break;
		}
	}
	fclose(f4);
	fclose(f3);
	fclose(f2);
	fclose(f1);
	fclose(f);

	return 0;
}
