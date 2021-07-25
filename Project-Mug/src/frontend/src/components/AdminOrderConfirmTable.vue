<template>
  <div class="confirm-table-card-container">
    <div class="confirm-table-card">
      <div class="card-left">
        <div class="orderlist-wrapper">
          <div class="orderlist-header">
            <div class="header-nav">
              <ul>
                <li class="active">All</li>
                <li>승인대기</li>
                <li>승인완료</li>
                <li>승인취소</li>
              </ul>
            </div>
          </div>
          <div class="orderlist-body">
            <table class="orderlist-table">
              <colgroup>
                <col class="selection-col" />
              </colgroup>

              <thead class="table-header">
                <tr>
                  <th class="selection-column">
                    <input type="checkbox" name="" id="" />
                  </th>
                  <th class="id">Id</th>
                  <th class="customer">Customer</th>
                  <th class="status">Status</th>
                  <th class="amount">Amount</th>
                  <th class="date">Date</th>
                  <th class="action">Action</th>
                </tr>
              </thead>

              <tbody class="table-body">
                <tr>
                  <td class="selection-column"><input type="checkbox" name="" id="" /></td>
                  <td class="id"><span>1</span></td>
                  <td class="customer"><span>1호점</span></td>
                  <td class="status"><span class="status success">승인완료</span></td>
                  <td class="amount"><span>20000원</span></td>
                  <td class="date"><span>2021 07 26</span></td>
                  <td class="action"><span>삭제하기</span></td>
                </tr>
                <tr>
                  <td class="selection-column"><input type="checkbox" name="" id="" /></td>
                  <td class="id"><span>1</span></td>
                  <td class="customer"><span>1호점</span></td>
                  <td class="status"><span class="status success">승인완료</span></td>
                  <td class="amount"><span>20000원</span></td>
                  <td class="date"><span>2021 07 26</span></td>
                  <td class="action"><span>삭제하기</span></td>
                </tr>
                <tr>
                  <td class="selection-column"><input type="checkbox" name="" id="" /></td>
                  <td class="id"><span>1</span></td>
                  <td class="customer"><span>1호점</span></td>
                  <td class="status"><span class="status success">승인완료</span></td>
                  <td class="amount"><span>20000원</span></td>
                  <td class="date"><span>2021 07 26</span></td>
                  <td class="action"><span>삭제하기</span></td>
                </tr>
                <tr>
                  <td class="selection-column"><input type="checkbox" name="" id="" /></td>
                  <td class="id"><span>1</span></td>
                  <td class="customer"><span>1호점</span></td>
                  <td class="status"><span class="status success">승인완료</span></td>
                  <td class="amount"><span>20000원</span></td>
                  <td class="date"><span>2021 07 26</span></td>
                  <td class="action"><span>삭제하기</span></td>
                </tr>
                <tr>
                  <td class="selection-column"><input type="checkbox" name="" id="" /></td>
                  <td class="id"><span>1</span></td>
                  <td class="customer"><span>1호점</span></td>
                  <td class="status"><span class="status success">승인완료</span></td>
                  <td class="amount"><span>20000원</span></td>
                  <td class="date"><span>2021 07 26</span></td>
                  <td class="action"><span>삭제하기</span></td>
                </tr>
                <tr>
                  <td class="selection-column"><input type="checkbox" name="" id="" /></td>
                  <td class="id"><span>1</span></td>
                  <td class="customer"><span>1호점</span></td>
                  <td class="status"><span class="status success">승인완료</span></td>
                  <td class="amount"><span>20000원</span></td>
                  <td class="date"><span>2021 07 26</span></td>
                  <td class="action"><span>삭제하기</span></td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="orderlist-footer"></div>
        </div>
      </div>
      <div class="card-right">
        <div class="order-summmary-wrapper">
          <div class="order-summmary">
            <div class="summary-card-content">
              <h1 class="card-title">TEST</h1>
              <div class="card-body">
                <div class="body-content">
                  <ul class="content-list">
                    <li>
                      <span class="content-list-title">승인대기</span>
                      <span class="content-list-text">500개</span>
                    </li>
                    <li>
                      <span class="content-list-title">승인완료</span>
                      <span class="content-list-text">500개</span>
                    </li>
                    <li>
                      <span class="content-list-title">승인취소</span>
                      <span class="content-list-text">500개</span>
                    </li>
                    <li>
                      <span class="content-list-title">총</span>
                      <span class="content-list-text">500개</span>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    * {
      box-sizing: border-box;
    }

    .confirm-table-card-container {
      background-color: map-get($map: $theme, $key: "content-background");
      box-shadow: $shadow-light;
      padding: 1rem;
      border-radius: 6px;
      transition: all 0.3s ease-in-out;
      position: relative;
      display: flex;
      flex-direction: column;
      box-sizing: border-box;
      color: map-get($map: $theme, $key: "text-light");
      width: 100%;
      max-width: 1020px;
      margin: 1rem auto;

      .confirm-table-card {
        display: flex;

        .card-left {
          max-width: 70%;
          width: 100%;
          margin-right: 1rem;

          .orderlist-wrapper {
            width: 100%;
            display: flex;
            flex-direction: column;

            .orderlist-header {
              display: flex;
              border-bottom: 1px solid map-get($map: $theme, $key: "border");

              .header-nav {
                display: flex;
                align-items: center;

                ul {
                  list-style: none;
                  display: flex;

                  li {
                    color: map-get($map: $theme, $key: "text-lighter");
                    font-weight: normal;
                    position: relative;
                    padding: 1rem 1.2rem;
                    user-select: none;
                    cursor: pointer;
                    transition: all 0.2s linear;
                    border-radius: 6px 6px 0 0;

                    &:hover {
                      background-color: rgba(map-get($theme, "background"), 0.4);
                      border-bottom: 2px solid rgba($main-color, 0.4);
                      font-weight: bold;
                    }

                    &.active {
                      border-bottom: 2px solid $main-color;
                      color: $main-color;
                      font-weight: bolder;
                      background-color: map-get($map: $theme, $key: "background");
                    }
                  }
                }
              }
            }

            .orderlist-body {
              padding: 1rem;
              background-color: map-get($map: $theme, $key: "background");
              width: 100%;
              height: 100%;
              border-radius: 0 0 6px 6px;

              .orderlist-table {
                width: 100%;
                table-layout: auto;

                .selection-col {
                  width: 32px;
                }

                .selection-column {
                }

                .table-header {
                  color: map-get($map: $theme, $key: "text");
                  white-space: nowrap;
                  user-select: none;

                  tr th {
                    padding: 1rem 0;
                    text-align: center;
                    font-size: 0.9rem;
                    font-weight: bold;
                    background-color: map-get($map: $theme, $key: "content-background");

                    &:first-child {
                      border-radius: 6px 0 0 6px;
                    }

                    &:last-child {
                      border-radius: 0 6px 6px 0;
                    }

                    &.selection-column {
                      width: 5%;
                    }
                    &.id {
                      width: 5%;
                    }
                    &.customer {
                      width: 20%;
                    }
                    &.status {
                      width: 15%;
                    }
                    &.amount {
                      width: 15%;
                    }
                    &.date {
                      width: 20%;
                    }
                    &.action {
                      width: 20%;
                    }
                  }
                }

                .table-body {
                  padding: 1rem;
                  text-align: center;
                  font-size: 0.85rem;

                  tr:hover {
                    background-color: darken($color: map-get($theme, "background"), $amount: 5%);
                  }

                  tr td {
                    padding: 1rem 0;
                    cursor: pointer;

                    &:first-child {
                      border-radius: 6px 0 0 6px;
                    }

                    &:last-child {
                      border-radius: 0 6px 6px 0;
                    }

                    &:hover {
                      background-color: darken($color: map-get($theme, "background"), $amount: 10%);
                    }

                    &.selection-column {
                      width: 5%;
                    }
                    &.id {
                      width: 5%;
                    }
                    &.customer {
                      width: 20%;
                    }
                    &.status {
                      width: 15%;
                    }
                    &.amount {
                      width: 15%;
                    }
                    &.date {
                      width: 20%;
                    }
                    &.action {
                      width: 20%;
                    }
                  }
                }
              }
            }

            .orderlist-footer {
            }
          }
        }

        .card-right {
          max-width: 30%;
          width: 100%;

          .order-summmary-wrapper {
            position: relative;
            max-width: 350px;
            width: 100%;
            height: 100%;

            .order-summmary {
              background-color: map-get($map: $theme, $key: "background");
              border-radius: 6px;
              padding: 1rem;
              height: 100%;

              .summary-card-content {
                margin: 0 auto;
                display: flex;
                flex-direction: column;
                height: 100%;

                .card-title {
                  font-size: 1rem;
                  font-weight: 500;
                  color: map-get($map: $theme, $key: "text");
                  margin-bottom: 25px;
                }

                .card-body {
                  list-style: none;
                  position: relative;
                  background-color: map-get($map: $theme, $key: "content-background");
                  border-radius: 6px;
                  box-shadow: $shadow-lighter;
                  padding: 1rem;
                  height: 100%;

                  .body-content .content-list {
                    list-style: none;

                    li {
                      margin-bottom: 18px;
                      display: flex;
                      justify-content: space-between;
                      align-items: center;
                      padding: 1rem;

                      &:last-child {
                        border-top: 1px solid map-get($map: $theme, $key: "border");
                      }

                      .content-list-title {
                      }

                      .content-list-text {
                        color: map-get($map: $theme, $key: "text");
                        font-weight: bolder;
                        font-size: 1.05rem;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
