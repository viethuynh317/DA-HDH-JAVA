package CayKhung;

import java.awt.Label;
import javax.swing.JFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;



public class TimeChart extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame();
	public static long threadTime;
	public static long nonThreadTime;
	
  public TimeChart(long threadTime, long nonThreadTime) {
	  TimeChart.threadTime = threadTime;
	  TimeChart.nonThreadTime = nonThreadTime;
	  initViewTimeChart();
  }

  public void initViewTimeChart() {
	  ChartPanel chartPanel = new ChartPanel(createChart());
      chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));   
      Label lb1 = new Label(String.valueOf(threadTime) + " ms");
      Label lb2 = new Label(String.valueOf(nonThreadTime) + " ms");
      lb1.setBounds(150, 200, 50, 20);
      lb2.setBounds(440, 200, 50, 20);
      frame.setSize(600, 400);
      frame.add(lb1);
      frame.add(lb2);
      frame.add(chartPanel);
      frame.setLocationRelativeTo(null);
      frame.setResizable(false);
      frame.setVisible(true);
  }

  public static JFreeChart createChart() {
      JFreeChart barChart = ChartFactory.createBarChart(
              "BIỂU ĐỒ SO SÁNH TỐC ĐỘ THỰC THI",
              "Phương pháp", "Thời gian(ms)",
              createDataset(), PlotOrientation.VERTICAL, false, false, false);  
      return barChart;
  }
  
  private static CategoryDataset createDataset() {
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
      dataset.addValue(threadTime  , "Thời gian(ms)", "MultiThread");
      dataset.addValue(nonThreadTime, "Thời gian(ms)", "Non-MultiThread");
      return dataset;
  }
}
