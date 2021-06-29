<template>
  <div class="charts-wrapper">
    <!-- Pie Chart -->
    <div class="inner-box">
      <div class="box-title">
        <h3>Pie Chart</h3>
        <div>Usage Rank</div>
      </div>
      <div class="box-chart pie-chart">
        <apexchart
          v-if="renderComponent"
          width="100%"
          :options="updatePieChartTheme"
          :series="pieChart.series"
          class="chart"
        ></apexchart>
      </div>
    </div>

    <!-- Line Chart -->
    <div class="inner-box">
      <div class="box-title">
        <h3>Line Chart</h3>
        <div>Recent Usage Statistics</div>
      </div>
      <div class="box-chart line-chart ">
        <apexchart
          v-if="renderComponent"
          width="100%"
          :options="updateLineChartTheme"
          :series="lineChart.series"
          class="chart"
        ></apexchart>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import VueApexCharts from "vue-apexcharts";

Vue.use(VueApexCharts);
Vue.component("apexchart", VueApexCharts);

export default {
  data() {
    return {
      renderComponent: true,
      pieChart: {
        series: [44, 55, 41, 17, 15],
        chartOptions: {
          chart: {
            type: "donut",
            background: "transparent",
          },
          labels: ["Cup A", "Cup B", "Cup C", "Cup D", "Cup E"],
          dataLabels: {
            enabled: false,
          },
          responsive: [
            {
              breakpoint: 480,
              options: {
                chart: {
                  width: 200,
                },
                legend: {
                  position: "bottom",
                },
              },
            },
          ],
          theme: {
            mode: "dark",
          },
        },
      },
      lineChart: {
        options: {
          chart: {
            type: "area",
            background: "transparent",
          },
          dataLabels: { enabled: false, style: { colors: ["#000"] } },
          xaxis: {
            categories: [1, 2, 3, 4, 5, 6],
          },
          stroke: {
            curve: "smooth",
          },
          theme: {
            mode: "light",
          },
        },
        series: [
          {
            name: "Cup A",
            data: [30, 40, 45, 30, 25, 40],
          },
          {
            name: "Cup B",
            data: [45, 50, 70, 91, 49, 60],
          },
          {
            name: "Cup C",
            data: [30, 10, 45, 60, 100, 91],
          },
        ],
      },
    };
  },
  computed: {
    updatePieChartTheme() {
      if (this.$store.state.theme === "theme-light") {
        return {
          chart: {
            type: "donut",
            background: "transparent",
          },
          labels: ["Cup A", "Cup B", "Cup C", "Cup D", "Cup E"],
          dataLabels: {
            enabled: false,
          },
          responsive: [
            {
              breakpoint: 480,
              options: {
                chart: {
                  width: 200,
                },
                legend: {
                  position: "bottom",
                },
              },
            },
          ],
          theme: {
            mode: "light",
          },
        };
      } else {
        return {
          chart: {
            type: "donut",
            background: "transparent",
          },
          labels: ["Cup A", "Cup B", "Cup C", "Cup D", "Cup E"],
          dataLabels: {
            enabled: false,
          },
          responsive: [
            {
              breakpoint: 480,
              options: {
                chart: {
                  width: 200,
                },
                legend: {
                  position: "bottom",
                },
              },
            },
          ],
          theme: {
            mode: "dark",
          },
        };
      }
    },
    updateLineChartTheme() {
      if (this.$store.state.theme === "theme-light") {
        return {
          chart: {
            type: "area",
            background: "transparent",
          },
          dataLabels: { enabled: false, style: { colors: ["#000"] } },
          xaxis: {
            categories: [1, 2, 3, 4, 5, 6],
          },
          stroke: {
            curve: "smooth",
          },
          theme: {
            mode: "light",
          },
        };
      } else {
        return {
          chart: {
            type: "area",
            background: "transparent",
          },
          dataLabels: { enabled: false, style: { colors: ["#000"] } },
          xaxis: {
            categories: [1, 2, 3, 4, 5, 6],
          },
          stroke: {
            curve: "smooth",
          },
          theme: {
            mode: "dark",
          },
        };
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");

    * {
      box-sizing: border-box;
    }
    .charts-wrapper {
      width: 100%;
      max-width: 1020px;
      margin: 1rem auto;
      display: flex;
      justify-content: space-between;

      .inner-box {
        background-color: map-get($map: $theme, $key: "content-background");
        color: map-get($map: $theme, $key: "text");
        position: relative;
        border: 1px solid map-get($map: $theme, $key: "content-blocked");
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 20px;
        box-shadow: $shadow-lighter;
        border-radius: 6px;

        .box-title {
          text-align: left;
          width: 100%;
          padding: 5px 15px;
          display: flex;
          flex-direction: column;
          align-items: flex-start;
          justify-content: center;

          h3 {
            font-weight: bold;
            font-size: 1.2rem;
            letter-spacing: 0.1rem;
          }
          div {
            color: map-get($map: $theme, $key: "text-light");
            font-size: 0.8rem;
            margin: 0.7rem 0;
          }
        }

        .box-chart {
          padding: 1.2rem;
        }

        .pie-chart {
          width: 400px;
        }

        .line-chart {
          width: 520px;
        }
      }
    }
  }
}
</style>
