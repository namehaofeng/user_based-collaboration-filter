package xsd;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCollaborationFilter {
	public static void main(String[] args) throws IOException {
		int k=10;
		UserCollaborationFilter x= new UserCollaborationFilter();
		Map<Double, Integer> map= new HashMap<>();
		double xiangsidu=0;
		double minx=0;
		List<String> list=new ArrayList<>();
		Map<String, String> a = new HashMap<>();
		a.put("31", "2.5");
		a.put("1029", "3");
		a.put("1061", "3");
		a.put("1129", "2");
		a.put("1172", "4");
		a.put("1263", "2");
		a.put("1287", "2");
		a.put("1293", "2");
		a.put("1339", "3.5");
		a.put("1343", "2");
		a.put("1371", "2.5");
		a.put("1405", "1");
		a.put("1953", "4");
		a.put("2105", "4");
		a.put("2150", "3");
		a.put("2193", "2");
		a.put("2294", "2");
		a.put("2455", "2.5");
		a.put("2968", "1");
		a.put("3671", "3");
		
		
		
		

		List<Map<String, String>> ss=x.cldysj();
		Map<String, String> b=new HashMap<>();
		for(int i=0;i<ss.size();i++){
			
			b=ss.get(i);
				list=x.total(a,b);	
				xiangsidu=x.comput(x.con(a, list), x.con(b, list));
				minx=xiangsidu;
				map.put(minx, i);
		
		}
		
		List<Double> li= new ArrayList<>();
		for (Entry<Double, Integer> entry : map.entrySet()) { 
	     	 
	     	   li.add(entry.getKey());
	     	 }
		double sz[]= new double[li.size()];
		for(int i=0;i<li.size();i++){
			sz[i]=li.get(i);
		}
		
		for(int i=0;i<sz.length;i++){//将用户按相似度排序
			for(int j=i+1;j<sz.length;j++){
				if(sz[j]>sz[i]){
					double temp;
					temp=sz[i];
					sz[i]=sz[j];
					sz[j]=temp;
					
				}
			}
			
		}
		
		List<Double> lii= new ArrayList<>();
		for(int i=0; i<sz.length;i++){
			lii.add(sz[i]);
		}//此时li集合中存放的是计算得到的相似度（从大到小排列）
		
		System.out.println("相似度前10的用户分别是");
		
		Map<Map<String, String>, Double> ma= new HashMap<>();
		for(int i=0;i<k;i++){//选取top-K
			System.out.println("用户："+ss.get(map.get(lii.get(i)))+"相似度:"+lii.get(i));
			
			ma.put(ss.get(map.get(lii.get(i))), lii.get(i));
		}
		List<String> liss= new ArrayList<>();
		for (Entry<Map<String, String>, Double> entry : ma.entrySet()) { 
			
			for(Entry<String, String> entry1 : entry.getKey().entrySet()) {
				for(Entry<String, String> entrya : a.entrySet()){
					if(entrya.getKey().equals(entry1.getKey())){
						continue;
					}else{
						liss.add(entry1.getKey());//liss用来存放a用户不包含的元素
						break;
					}
				}
			}
     	  
     	 }
//		for(int i=0;i<liss.size();i++){
//			System.out.println(liss.get(i)+"===========");
//		}
		Map<Double, String> mm=new HashMap<>();
		for(int i=0;i<liss.size();i++){//计算a中不含有而邻居用户含有的电影的推荐指数
			double d=0;
			for (Entry<Map<String, String>, Double> entry : ma.entrySet()) { 
				
				for(Entry<String, String> entry1 : entry.getKey().entrySet()) {
					if(entry1.getKey().equals(liss.get(i))){
						d=((Double.valueOf(entry1.getValue()))*entry.getValue())+d;
						break;
					}
					}
				}
		//	System.out.println("电影"+liss.get(i)+"的相似度为"+d);
			
			mm.put(d, liss.get(i));
			
		}
		List<Double> lisi= new ArrayList<>();
		for (Entry<Double, String> entry : mm.entrySet()) { 
			lisi.add(entry.getKey());
     	 }
		//////
		double szz[]= new double[lisi.size()];
		for(int i=0;i<lisi.size();i++){
			szz[i]=lisi.get(i);
		}
		
		for(int i=0;i<szz.length;i++){//推荐指数排序 
			for(int j=i+1;j<szz.length;j++){
				if(szz[j]>szz[i]){
					double temp;
					temp=szz[i];
					szz[i]=szz[j];
					szz[j]=temp;
					
				}
			}
			
		}
		
		for(int i=0;i<szz.length;i++){
			System.out.println("给该用户推荐的电影分别是:"+mm.get(szz[i])+"推荐指数是:"+szz[i]);
		}
	
}
	public void not_exict(){
		
	}
	public double comput(List<Double> list1,List<Double> list2){//计算向量余弦
		double result=0;
		double fenzi=0;
		double fenmu=1;
		double zuofenmu=0;
		double youfenmu=0;
		for(int i=0;i<list1.size();i++){//分子的计算过程
				fenzi=list1.get(i)*list2.get(i)+fenzi;
				
		}
		
		for(int i=0;i<list1.size();i++){//左分母的计算过程
			
				zuofenmu=(list1.get(i)*list1.get(i))+zuofenmu;
		}
		for(int i=0;i<list2.size();i++){//右分母的计算过程
			
			youfenmu=(list2.get(i)*list2.get(i))+youfenmu;
	}
		
		fenmu=Math.sqrt(zuofenmu)*Math.sqrt(youfenmu);
		if (fenmu==0){
			return 0;
		}else{
			result=fenzi/fenmu;
			
		}
		
		return result;
	}
	
	public List<Double> con(Map<String, String> a,List<String> list){//计算每个向量的0、1个数
		List<Double> listt= new ArrayList<>();
		for(int i=0;i<list.size();i++){
			if(a.get(list.get(i))!=null){
				listt.add(Double.valueOf(a.get(list.get(i))));
			}else listt.add(0.0);
		}

		return listt;
		
	}
	
	public List<String> total(Map<String, String> map, Map<String, String> map1){//将向量拼接
		List<String> list1= new ArrayList<>();
		List<String> list2= new ArrayList<>();
		List<String> list= new ArrayList<>();
		 for (Entry<String, String> entry : map.entrySet()) { 
      	   list1.add(entry.getKey());
      	 }
		 for (Entry<String, String> entry1 : map1.entrySet()) { 
	      	   list2.add(entry1.getKey());
	      	 }
		 
		for(int i=0;i<list2.size();i++){
			
			int ss=0;
			for(int j=0;j<list1.size();j++){
				if((list2.get(i).equals(list1.get(j)))){
					ss++;
				}
			}
			if(ss==0){
				int n=0;
				
					for(int j=0;j<list.size();j++){
						if(list.get(j).equals(list2.get(i))){
							n++;
						}
					}
					if(n==0){
						list.add(list2.get(i));
					}
					
				}
				
			
			
		}
		for(int i=0;i<list1.size();i++){
			int n=0;
			
				for(int j=0;j<list.size();j++){
					if(list.get(j).equals(list1.get(i))){
						n++;
					}
				}
				if(n==0){
					list.add(list1.get(i));
				}
				
			
			
		}

		return list;
	}
	
	public List<Map<String, String>> cldysj() throws IOException{//将电影数据集转换成目标格式
		File file = new File("C:/Users/haofeng/Desktop/ratings.txt");
		BufferedReader reader = null;
	    String tempString = null;
	    reader = new BufferedReader(new FileReader(file));
	    List<String[]> list = new ArrayList<>();
        while ((tempString = reader.readLine()) != null) {       	
        	 String str=	tempString.replaceAll("\\s", ",");
			 str=str.replace(",,", ",");
			 String[] strA=str.split(",");
			 list.add(strA);
        }
       // System.out.println(list.size());
        reader.close();
        int m=0;
        List<Map<String, String>> listt= new ArrayList<>();
        for(int i=0;i<list.size();i=m){
        	Map<String, String> map= new HashMap<>();
        	map.put(list.get(i)[1], list.get(i)[2]);
        	for(int j=i+1;j<list.size();j++){
        		if(j==(list.size()-1)){
    				m=list.size();
    				break;
    			}
        		else if(list.get(i)[0].equals(list.get(j)[0])){
        			map.put(list.get(j)[1], list.get(j)[2]);
        			m=i;
        		}else{
        			m=j;
        			break;
        		}
        	}
        	
        	listt.add(map);
        }
        System.out.println(listt.get(0).size());
       return listt;
       
	}
}
