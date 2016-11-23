clear all
close all
clc
close(figure)
CH_pe=0;
close(figure)
for w=1:1
%% get the data from the mobility model %%

    pc=0;delay=0;delay_n=0; 
delay_b=0;delayb=0;CH_dis=0;delay_prov=0;no_hops=0;
 [t,n,x,y,s,d]=textread('C:\Users\Raghad Baiad\Dropbox\Dr.Rabeb-Raghad\Matlab-webservice\output\f.txt','%f%f%f%f%f%f'); % read the text files where the simulation has been done
   figure(1)
   plot(x,y)
   providerper=50
   target=0
nodes=n+1; % so the nodes start from 1 to 30 instead of 0 to 29
max(nodes) % to get the maximum number of the nodes
[t,n,x,y,s,d];% this text file has the time,the nodes,the coordination of these nodes at certain time , the speed and the direction
die=round(d); % make the direction integer so we can sort the nodes baesd on their direction

%%set the parameters: %%
u=1;r=1;ub=1; % initiate counters
total_dist= sqrt(((995-50).^2) + ((74-50).^2))+sqrt(((0).^2) + ((0).^2))+sqrt(((0).^2) + ((0).^2)); %calculate the total path distance
total_avg_speed= sum(s)/length(s);
%%%%plot data %%
for i=1:max(nodes)
    plot(x(i),y(i),'bo','linewidth',5);hold on
end
                axis([0 1000  -5 100])
ylabel('Lane height','fontsize',10)
xlabel('Lane width','fontsize',10)

%%Run the data i seconds>>>>>>>>> for every second the program will
%%calculate the distance and decide the neighbours and do the other stuff
  u=1;r=1;ub=1;
 for i=5:5
     
   %  Starting to implement the network layer protocol OLSR that is dividing the nodes into clusters each cluster had its own cluster Head and MPR , they will organize the data transmmision 
   %inside each custer if the node wants to send data to certain destination it has to send the data to the cluster head first then the cluster head will send it to the MPR that will send it 
   % another cluster 
   
     fir=0;se=0; nodea=0;
                nodeb=0;
                MPRa=0;
                mprc=0;mprd=0;mpr=0;MPR=0;
                CH=0;
                CH2=0;
                MPR_Per_1 =0;
                MPR_per=0;
              time=i;
    cnt_o=u;
    o=cnt_o+(max(nodes)-1);
    CF=0;N=0; %reinitiate the Qos
    %% calculate the current speed,distance and remaining distance
    for l=1:max(nodes)
        cu_d(l)=s(ub)*i; % current direction
        cu_s(l)=s(ub); % current speed
        r_d(l)=total_dist-cu_d(l); %remaining distance for each node to complete the path
        ub=ub+1;
    end
    %% Calculate the distance between nodes in oreder to decide the neighbours %%
    for j=1:max(nodes) % x position values
        for k=max(nodes):-1:1 %y position values
            dist(j,k)= sqrt(((x(o)-x(u)).^2) + ((y(o)-y(u)).^2)) ; %calcuate the distance between two points
             cur_speed(j,k)=s(u);
              cur_dist(j,k)= cur_speed(j,k) *time; %node distance at time=i
             dir(j,k)=die(u);
            o=o-1; % increase the y-position counter
            if dist(j,k) == 0  %choose the neighbours
                N1(j,k)=0 ; %assign value of 1 for each neighbour 
            elseif dist(j,k)<=300 % choose the nodes inside cluster all nodes within 300 m range are considered to be neighbours
                N1(j,k)=1;
                else
               N1(j,k)=0;
            end
            end
          u=u+1;
          o=cnt_o+(max(nodes)-1);   
    end
    dir_T=dir.';
       dir_T;
       dir;
       %% Since the Cluster Head election should be stricted to direction, new Neighbouring matrix is constructed, based on the distance and direction
       
        for i=1:max(nodes)
             for j=1:max(nodes)
                     
                         if dir_T(i,j)==dir(i,j) & N1(i,j)==1  %if the neighbour nodes in the same direction they'll go into the list N 
                             N(i,j)=1;
                         else N(i,j)=0;
                     
                         end
                     end
             end
       
     
             %% Calculate the Cost to Forward equation for each node at i second
             % each cluster head will be choosen depending on certain
             % factors for instance it has to have the best average speed,
             % has the largest number of neighbors
            for b=1:max(nodes)
                ne(b)=sum(N(b,:));
                av_s(b)=cu_s(b)/total_avg_speed;    
            if  r_d(b)~=0 &ne(b) ~=0
             CFb(b)= (1/r_d(b))*(1/(ne(b)/max(nodes)))*av_s(b);
                        end
            end
            CFb(CFb==0)=40;
            
            %% CH voting code 
            % each node will vote for himself and for its neighbor 
            cur_dist=cur_dist.';
          avg_node_speed1 = cur_speed/total_avg_speed;
            avg_node_speed2=avg_node_speed1.';
          for z=1:max(nodes)
              for v=1:max(nodes) 
              if N(z,v)>0| z==v % each node vote for it self and its neighbours 
              rem_dist(z,v)=total_dist-cur_dist(z,v); %remaining distance for the node to complete the path
              nodes_no(z,v)= sum(N(z,:)); % count the number of neighbors for each node
                if rem_dist(z,v)~=0 & nodes_no(z,v)~=0 & avg_node_speed2(z,v)~=0
             CF(z,v)= (1/rem_dist(z,v))*(1/(nodes_no(z,v)/max(nodes)))*avg_node_speed2(z,v);% calculate the cost forward equation
             end
              else CF(z,v)=40;
                     
              end
              end      
          end
          
                   
    N1;
    dir_T;
    N;
         CF;
        CF_T=CF.';
        
        [max_CF  CH ] = min(CF_T,[],1); % choose the cluster head with the mininm cost to forward value
          
          
         
          %%%%%%%% CF for all nodes to choose optimum path 
          
        % after choosing the cluster heads we should the MPR nodes, these nodes are responsible to connect the CH in the network
        %so we'll have complete connected graph 
                 

         CH(CH==0)=[];
         CH2=unique(CH);
           
        for i=1:length(CH)
            for j=length(CH):-1:1
                if i~=j
                if N(CH(i),CH(j))==0 
                    if CH(i)~=CH(j)
                     for k=1:length(CH)
                        if N(CH(i),k)==1 && N(CH(j),k)==1
                            MPRb(i)=k;
                             fir(i)=CH(i);
                             se(i)=CH(j);
                        end
                     end
                     
                    end
                    
                end
                end
            end
              
        end
                    
        q=0;
       MPRb ;
       CHa=CH;
           
                MPRb;
                
          CH2=CH;
          nod=[fir;se];

          nod=nod.';
          ba=sort(nod,2);
          bb = nod(any(nod,2),:);

          uniquenod = unique(bb,'rows');
          uniquenod;
             CH2=unique(CH);

            if uniquenod>0
              d= uniquenod  ;  
              A=d;
            else
                d=0;
                A=0;
            end
              if d>0
             A(:,[1,2])=A(:,[2,1]);
              end
         for i=1:length(CH2)
              
                  CH1(i,:)=N(CH2(i),:);
         end
         
        if d==0 
            coma=0;
            comb=0;
        end
              for i=1:length(CH2)
                  for j=1:length(CH2)
                      if i~=j
                          if N(CH2(i),CH2(j))==0 
                           if uniquenod>0
                         coma= ismember([CH2(i),CH2(j)],d(:,:),'rows');
                         comb=ismember([CH2(i),CH2(j)],A(:,:),'rows');
                           end
                         if (coma==0 & comb==0 )  | d==0
                             [ CH2(i) , CH2(j)];
                          nra=CH1(i,:);
                          nrb=CH1(j,:);
                          [row col]=find(nra);
                          nraa=col;
                          [row col]=find(nrb);
                          nrbb=col;
                          for k=1:length(nraa)
                              for z=1:length(nrbb)
                          if nraa(k)==nrbb(z)
                              mpr(i,j)=nraa(k);
                          else                          
                          if N(nraa(k),nrbb(z))==1
                             e=[ nraa(k) nrbb(z)];
                             c= [ CFb(nraa(k)) CFb(nrbb(z))];
                             q(k,z)=nraa(k);
                             g= CFb(nraa(k))+ CFb(nrbb(z));
                             r(k,z)=g;
                          end     
                          end
                              end
                          end
                         
                      [min_CFb  rb ] =min(r(r>0));
                      [ro,co] = find(r == min_CFb);
                      qv=q(ro,co) ;
                      qv=unique(min(qv));
                      
                      if qv>0
                          mpr(i,j)=qv;
                   
                          end     
                         end
                      
                         end
                          end
              r=0;nrb=0;e=0;g=0;b=0;q=0;qv=0;c=0;

                  end 
                  nra=0;
              end
                  mpr=unique(mpr);
                 
                  if size(mpr)~=size(MPRb)
                      mpr=mpr.';
                       MPR=[MPRb mpr];
                  end
                  MPR=[MPRb mpr];
                   MPRt=[MPRb mpr  CH2];
                  MPR(MPR==0)=[];
                                    MPRt(MPRt==0)=[];

                  
                  MPRt=unique(MPRt);
                                    MPR=unique(MPR);

            MPR_Per_1= (length(MPRt)/max(nodes))*100;
            for i=1:length(MPRt)
    plot(x(MPRt(i)),y(MPRt(i)),'ro','linewidth',12);hold on
end


for i=1:max(nodes)
    text(x(i),y(i),num2str(i),'linewidth',12);hold on
end
 end
end
%%
