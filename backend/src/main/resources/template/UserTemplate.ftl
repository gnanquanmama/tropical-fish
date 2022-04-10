 <!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <style>
      * {
        margin: 0;
      }
      body {
        height: 100vh;
        font-family: 'Lato', sans-serif;
        margin: 0;
      }
      h1 {
        font-size: 32px;
        margin-bottom: 12px;
        text-align: center;
      }
      h3 {
        margin-top: 12px;
      }
      .table-list,
      .form-data {
        width: 100%;
        margin-bottom: 20px;
        border-bottom: 1px solid;
        border-left: 1px solid;
      }
      th,
      td {
        border-top: 1px solid;
        border-right: 1px solid;
      }
      .audit {
        display: flex;
        padding: 12px;
      }
      .audit > span {
        flex-basis: 50%;
      }
      .comments {
        padding: 0 12px;
      }
    </style>
    <title>Document</title>
  </head>
  <body>

    <div>
      <table class="table-list" cellpadding="4" cellspacing="0">
        <tr style="background-color: #c0c0c0">
          <th>序号</th>
          <th>手机号码</th>
          <th>昵称</th>
          <th>用户名称</th>
        </tr>
        <#list userList as ad>
          <tr>
            <td>${ad_index+1}</td>
            <td>${ad.mobileNumber}</td>
            <td>${ad.nickName}</td>
            <td>${ad.userName ! ''}</td>
          </tr>
        </#list>
      </table>
    </div>

  </body>
</html>
