<template>
  <div class="container">
    <div class="form-container partner-form-container" v-if="role === 'partner'">
      <router-link
        :to="{ name: 'join', query: { role: 'customer' } }"
        tag="button"
        class="overlay-top"
      >
        👈 사용자 회원가입하러가기</router-link
      >

      <form action="#">
        <h1>파트너 회원가입하기</h1>
        <div class="social-container">
          <div class="social-error" v-if="socialIconClicked">아직 지원하지 않는 기능입니다.</div>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon
          ></a>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon
          ></a>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon
          ></a>
        </div>

        <span>👇 이메일로 회원가입하기 👇</span>

        <!-- Partner ID -->
        <input
          type="email"
          placeholder="Partner ID (Email)"
          @keydown.enter.prevent="nextPwInput"
          @keyup="checkId"
          v-model="formData.joinId"
        />
        <span class="error-msg">{{ errors.joinId }}</span>

        <!-- Partner Password -->
        <span class="password-hint"
          >8자리 이상: 영어(대∙소문자), 숫자, 특수문자(~ ! @ # $ % ^ & *) 조합</span
        >
        <input
          type="password"
          placeholder="Partner Password"
          @keydown.enter.prevent="nextInput"
          @blur="checkPasswordForm"
          v-model="formData.joinPw"
        />
        <span class="error-msg">{{ errors.joinPw }}</span>

        <!-- Partner Password Check -->
        <input
          type="password"
          placeholder="Password Check"
          @keydown.enter.prevent="telInput"
          @blur="checkPasswordCheck"
          v-model="formData.joinPwChk"
        />
        <span class="error-msg">{{ errors.joinPwChk }}</span>

        <!-- Partner Tel -->
        <div class="num-input-wrapper">
          <span class="num-input-title">Tel</span>
          <input
            type="number"
            name="tel_first"
            class="num-input"
            v-model="formData.joinTel.first"
            placeholder="010"
            @keydown.enter.prevent="nextTelInput"
            @keyup="limitNumber"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_second"
            class="num-input"
            v-model="formData.joinTel.second"
            placeholder="0000"
            @keydown.enter.prevent="nextTelInput"
            @keyup="limitNumber"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_third"
            class="num-input"
            v-model="formData.joinTel.third"
            placeholder="0000"
            @keydown.enter.prevent="goPopup"
            @keyup="limitNumber"
          />
        </div>
        <span class="error-msg">{{ errors.joinTel }}</span>

        <!-- Partner Address -->
        <div class="address-input-wrapper">
          <input
            type="text"
            id="location"
            placeholder="주소"
            @click.prevent="goPopup"
            class="address-input"
            v-model="formData.joinAddress.address"
          />
          <button @click.prevent="goPopup" class="address-btn">매장찾기</button>
        </div>

        <div class="address-detail-input-wrapper">
          <input
            type="text"
            id="location_name"
            placeholder="카페 이름"
            v-model="formData.joinAddress.addressName"
          />

          <input
            type="hidden"
            v-model="formData.joinAddress.addressLat"
            id="location_lat"
            value=""
          />
          <input
            type="hidden"
            v-model="formData.joinAddress.addressLng"
            id="location_lng"
            value=""
          />
        </div>
        <span class="error-msg">{{ errors.joinAddress }}</span>

        <!-- Private Policy -->
        <div class="policy-wrapper">
          <h1 class="policy-title">개인정보 보호정책</h1>
          <textarea name="" id="" cols="30" rows="10" class="policy" readonly>
【개인정보 수집․이용 제공 동의서】
∎ 관련법규 : 개인정보 보호법 제15조, 제16조, 제 17조, 제21조, 제22조
 제15조(개인정보의 수집·이용)  ① 개인정보처리자는 다음 각 호의 어느 하나에 해당하는 경우에는 개인정보를 수집할 수 있으며 그 수집 목적의 범위에서 이용할 수 있다.
  1. 정보주체의 동의를 받은 경우
  2. 법률에 특별한 규정이 있거나 법령상 의무를 준수하기 위하여 불가피한 경우
  3. 공공기관이 법령 등에서 정하는 소관 업무의 수행을 위하여 불가피한 경우
  4. 정보주체와의 계약의 체결 및 이행을 위하여 불가피하게 필요한 경우
  5. 정보주체 또는 그 법정대리인이 의사표시를 할 수 없는 상태에 있거나 주소불명 등으로 사전 동의를 받을 수 없는 경우로서 명백히 정보주체 또는 제3자의 급박한 생명, 신체, 재산의 이익을 위하여 필요하다고 인정되는 경우
  6. 개인정보처리자의 정당한 이익을 달성하기 위하여 필요한 경우로서 명백하게 정보주체의 권리보다 우선하는 경우. 이 경우 개인정보처리자의 정당한 이익과 상당한 관련이 있고 합리적인 범위를 초과하지 아니하는 경우에 한한다.
  ② 개인정보처리자는 제1항제1호에 따른 동의를 받을 때에는 다음 각 호의 사항을 정보주체에게 알려야 한다. 다음 각 호의 어느 하나의 사항을 변경하는 경우에도 이를 알리고 동의를 받아야 한다.
  1. 개인정보의 수집ㆍ이용 목적
  2. 수집하려는 개인정보의 항목
  3. 개인정보의 보유 및 이용 기간
  4. 동의를 거부할 권리가 있다는 사실 및 동의 거부에 따른 불이익이 있는 경우에는 그 불이익의 내용

 제16조(개인정보의 수집 제한)  ① 개인정보처리자는 제15조제1항 각 호의 어느 하나에 해당하여 개인정보를 수집하는 경우에는 그 목적에 필요한 최소한의 개인정보를 수집하여야 한다. 이 경우 최소한의 개인정보 수집이라는 입증책임은 개인정보처리자가 부담한다.
  ② 개인정보처리자는 정보주체의 동의를 받아 개인정보를 수집하는 경우 필요한 최소한의 정보 외의 개인정보 수집에는 동의하지 아니할 수 있다는 사실을 구체적으로 알리고 개인정보를 수집하여야 한다.  <신설 2013. 8. 6.>
  ③ 개인정보처리자는 정보주체가 필요한 최소한의 정보 외의 개인정보 수집에 동의하지 아니한다는 이유로 정보주체에게 재화 또는 서비스의 제공을 거부하여서는 아니 된다.  <개정 2013. 8. 6.>

 제17조(개인정보의 제공)  ① 개인정보처리자는 다음 각 호의 어느 하나에 해당되는 경우에는 정보주체의 개인정보를 제3자에게 제공(공유를 포함한다. 이하 같다)할 수 있다.
  1. 정보주체의 동의를 받은 경우
  2. 제15조제1항제2호ㆍ제3호 및 제5호에 따라 개인정보를 수집한 목적 범위에서 개인정보를 제공하는 경우
  ② 개인정보처리자는 제1항제1호에 따른 동의를 받을 때에는 다음 각 호의 사항을 정보주체에게 알려야 한다. 다음 각 호의 어느 하나의 사항을 변경하는 경우에도 이를 알리고 동의를 받아야 한다.
  1. 개인정보를 제공받는 자
  2. 개인정보를 제공받는 자의 개인정보 이용 목적
  3. 제공하는 개인정보의 항목
  4. 개인정보를 제공받는 자의 개인정보 보유 및 이용 기간
  5. 동의를 거부할 권리가 있다는 사실 및 동의 거부에 따른 불이익이 있는 경우에는 그 불이익의 내용
  ③ 개인정보처리자가 개인정보를 국외의 제3자에게 제공할 때에는 제2항 각 호에 따른 사항을 정보주체에게 알리고 동의를 받아야 하며, 이 법을 위반하는 내용으로 개인정보의 국외 이전에 관한 계약을 체결하여서는 아니 된다.

 제21조(개인정보의 파기)  ① 개인정보처리자는 보유기간의 경과, 개인정보의 처리 목적 달성 등 그 개인정보가 불필요하게 되었을 때에는 지체 없이 그 개인정보를 파기하여야 한다. 다만, 다른 법령에 따라 보존하여야 하는 경우에는 그러하지 아니하다.
  ② 개인정보처리자가 제1항에 따라 개인정보를 파기할 때에는 복구 또는 재생되지 아니하도록 조치하여야 한다.
  ③ 개인정보처리자가 제1항 단서에 따라 개인정보를 파기하지 아니하고 보존하여야 하는 경우에는 해당 개인정보 또는 개인정보파일을 다른 개인정보와 분리하여서 저장ㆍ관리하여야 한다.
  ④ 개인정보의 파기방법 및 절차 등에 필요한 사항은 대통령령으로 정한다.

 제22조(동의를 받는 방법)  ① 개인정보처리자는 이 법에 따른 개인정보의 처리에 대하여 정보주체(제6항에 따른 법정대리인을 포함한다. 이하 이 조에서 같다)의 동의를 받을 때에는 각각의 동의 사항을 구분하여 정보주체가 이를 명확하게 인지할 수 있도록 알리고 각각 동의를 받아야 한다.  <개정 2017. 4. 18.>
  ② 개인정보처리자는 제1항의 동의를 서면(「전자문서 및 전자거래 기본법」 제2조제1호에 따른 전자문서를 포함한다)으로 받을 때에는 개인정보의 수집ㆍ이용 목적, 수집ㆍ이용하려는 개인정보의 항목 등 대통령령으로 정하는 중요한 내용을 행정안전부령으로 정하는 방법에 따라 명확히 표시하여 알아보기 쉽게 하여야 한다.  <신설 2017. 4. 18., 2017. 7. 26.>
  ③ 개인정보처리자는 제15조제1항제1호, 제17조제1항제1호, 제23조제1항제1호 및 제24조제1항제1호에 따라 개인정보의 처리에 대하여 정보주체의 동의를 받을 때에는 정보주체와의 계약 체결 등을 위하여 정보주체의 동의 없이 처리할 수 있는 개인정보와 정보주체의 동의가 필요한 개인정보를 구분하여야 한다. 이 경우 동의 없이 처리할 수 있는 개인정보라는 입증책임은 개인정보처리자가 부담한다.  <개정 2016. 3. 29., 2017. 4. 18.>
  ④ 개인정보처리자는 정보주체에게 재화나 서비스를 홍보하거나 판매를 권유하기 위하여 개인정보의 처리에 대한 동의를 받으려는 때에는 정보주체가 이를 명확하게 인지할 수 있도록 알리고 동의를 받아야 한다.  <개정 2017. 4. 18.>
  ⑤ 개인정보처리자는 정보주체가 제3항에 따라 선택적으로 동의할 수 있는 사항을 동의하지 아니하거나 제4항 및 제18조제2항제1호에 따른 동의를 하지 아니한다는 이유로 정보주체에게 재화 또는 서비스의 제공을 거부하여서는 아니 된다.  <개정 2017. 4. 18.>
  ⑥ 개인정보처리자는 만 14세 미만 아동의 개인정보를 처리하기 위하여 이 법에 따른 동의를 받아야 할 때에는 그 법정대리인의 동의를 받아야 한다. 이 경우 법정대리인의 동의를 받기 위하여 필요한 최소한의 정보는 법정대리인의 동의 없이 해당 아동으로부터 직접 수집할 수 있다.  <개정 2017. 4. 18.>
  ⑦ 제1항부터 제6항까지에서 규정한 사항 외에 정보주체의 동의를 받는 세부적인 방법 및 제6항에 따른 최소한의 정보의 내용에 관하여 필요한 사항은 개인정보의 수집매체 등을 고려하여 대통령령으로 정한다.  <개정 2017. 4. 18.>



<개인정보 수집․이용 제공 관련 사항>
1.	기본 개인정보 수집 • 이용 목적
1.1. 캠페인이 끝난 이후에도 컵이 반납되지 않을 시 연락을 취할 목적
1.2. 개인정보의 수집·이용 목적 중 재화나 서비스의 홍보 또는 판매 권유 등을 위하여 해당 개인정보를 이용하여 정보주체에게 연락할 수 있습니다.

2.	수집 • 이용하려는 개인정보 항목
2.1. 성명
2.2. 연락처
2.3. 이메일

3.	개인정보 이용 및 보유기간
3.1. 가입 시점으로부터 1년

4.	개인정보 제공 동의 거부 권리 및 동의 거부에 따른 제한 사항 
4.1. 위와 같이 개인정보를 수집 이용하는데 동의를 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 캠페인 관련 서비스 이용에 제한을 받을 수 있습니다.

※ 개인정보 제공자가 동의한 내용외의 다른 목적으로 활용하지 않으며, 제공된 개인정보의 이용을 거부하고자 할 때에는 개인정보 관리책임자를 통해 열람, 정정, 삭제를 요구할 수 있습니다.

본인은 본 「개인정보의 수집·이용·제3자 제공 동의서」내용을 읽고 명확히 이해하였으며, 이에 동의합니다.


위컵wecup
</textarea
          >
          <div class="policy-check-wrapper">
            <div class="policy-check">
              <input type="checkbox" name="" id="policy" v-model="formData.policyChk" />
              <label for="policy">위 개인정보 수집 · 이용에 동의합니다. (필수) </label>
            </div>
            <span class="error-msg">{{ errors.policyChk }}</span>
          </div>
        </div>

        <span class="error-msg" style="text-align:center;">{{ errors.response }}</span>
        <button @click.prevent="onSubmitForm">회원가입</button>
        <router-link :to="{ name: 'login' }" tag="span" class="caption"
          >계정이 이미 있어요</router-link
        >
      </form>
    </div>

    <div class="form-container customer-form-container" v-else-if="role === 'customer'">
      <router-link
        :to="{ name: 'join', query: { role: 'partner' } }"
        tag="button"
        class="overlay-top"
      >
        👉 파트너 회원가입하러가기</router-link
      >
      <form action="#">
        <h1>사용자 회원가입하기</h1>
        <div class="social-container">
          <div class="social-error" v-if="socialIconClicked">아직 지원하지 않는 기능입니다.</div>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fab', 'facebook-f']"></font-awesome-icon
          ></a>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fab', 'google-plus-g']"></font-awesome-icon
          ></a>
          <a href="#" @click="onClickSocialIcon"
            ><font-awesome-icon :icon="['fas', 'comment']"></font-awesome-icon
          ></a>
        </div>

        <span>👇 이메일로 회원가입하기 👇</span>

        <!-- ID -->
        <input
          type="email"
          placeholder="ID (Email)"
          @keydown.enter.prevent="nextPwInput"
          @keyup="checkId"
          v-model="formData.joinId"
        />
        <span class="error-msg">{{ errors.joinId }}</span>

        <!-- Password -->
        <span class="password-hint"
          >8자리 이상: 영어(대∙소문자), 숫자, 특수문자(~ ! @ # $ % ^ & *) 조합</span
        >
        <input
          type="password"
          placeholder="Password"
          @keydown.enter.prevent="nextInput"
          @blur="checkPasswordForm"
          v-model="formData.joinPw"
        />
        <span class="error-msg">{{ errors.joinPw }}</span>

        <!-- Password Check -->
        <input
          type="password"
          placeholder="Password Check"
          @keydown.enter.prevent="telInput"
          @blur="checkPasswordCheck"
          v-model="formData.joinPwChk"
        />
        <span class="error-msg">{{ errors.joinPwChk }}</span>

        <!-- Tel -->
        <div class="num-input-wrapper">
          <span class="num-input-title">Tel</span>
          <input
            type="number"
            name="tel_first"
            class="num-input"
            v-model="formData.joinTel.first"
            placeholder="010"
            @keydown.enter.prevent="nextTelInput"
            @keyup="limitNumber"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_second"
            class="num-input"
            v-model="formData.joinTel.second"
            placeholder="0000"
            @keydown.enter.prevent="nextTelInput"
            @keyup="limitNumber"
          />
          <div class="num-input-connector">
            <font-awesome-icon :icon="['fas', 'minus']"></font-awesome-icon>
          </div>
          <input
            type="number"
            name="tel_third"
            class="num-input"
            v-model="formData.joinTel.third"
            placeholder="0000"
            @keyup="limitNumber"
          />
        </div>
        <span class="error-msg">{{ errors.joinTel }}</span>

        <!-- Private Policy -->
        <div class="policy-wrapper">
          <h1 class="policy-title">개인정보 보호정책</h1>
          <textarea name="" id="" cols="30" rows="10" class="policy" readonly>
【개인정보 수집․이용 제공 동의서】
∎ 관련법규 : 개인정보 보호법 제15조, 제16조, 제 17조, 제21조, 제22조
 제15조(개인정보의 수집·이용)  ① 개인정보처리자는 다음 각 호의 어느 하나에 해당하는 경우에는 개인정보를 수집할 수 있으며 그 수집 목적의 범위에서 이용할 수 있다.
  1. 정보주체의 동의를 받은 경우
  2. 법률에 특별한 규정이 있거나 법령상 의무를 준수하기 위하여 불가피한 경우
  3. 공공기관이 법령 등에서 정하는 소관 업무의 수행을 위하여 불가피한 경우
  4. 정보주체와의 계약의 체결 및 이행을 위하여 불가피하게 필요한 경우
  5. 정보주체 또는 그 법정대리인이 의사표시를 할 수 없는 상태에 있거나 주소불명 등으로 사전 동의를 받을 수 없는 경우로서 명백히 정보주체 또는 제3자의 급박한 생명, 신체, 재산의 이익을 위하여 필요하다고 인정되는 경우
  6. 개인정보처리자의 정당한 이익을 달성하기 위하여 필요한 경우로서 명백하게 정보주체의 권리보다 우선하는 경우. 이 경우 개인정보처리자의 정당한 이익과 상당한 관련이 있고 합리적인 범위를 초과하지 아니하는 경우에 한한다.
  ② 개인정보처리자는 제1항제1호에 따른 동의를 받을 때에는 다음 각 호의 사항을 정보주체에게 알려야 한다. 다음 각 호의 어느 하나의 사항을 변경하는 경우에도 이를 알리고 동의를 받아야 한다.
  1. 개인정보의 수집ㆍ이용 목적
  2. 수집하려는 개인정보의 항목
  3. 개인정보의 보유 및 이용 기간
  4. 동의를 거부할 권리가 있다는 사실 및 동의 거부에 따른 불이익이 있는 경우에는 그 불이익의 내용

 제16조(개인정보의 수집 제한)  ① 개인정보처리자는 제15조제1항 각 호의 어느 하나에 해당하여 개인정보를 수집하는 경우에는 그 목적에 필요한 최소한의 개인정보를 수집하여야 한다. 이 경우 최소한의 개인정보 수집이라는 입증책임은 개인정보처리자가 부담한다.
  ② 개인정보처리자는 정보주체의 동의를 받아 개인정보를 수집하는 경우 필요한 최소한의 정보 외의 개인정보 수집에는 동의하지 아니할 수 있다는 사실을 구체적으로 알리고 개인정보를 수집하여야 한다.  <신설 2013. 8. 6.>
  ③ 개인정보처리자는 정보주체가 필요한 최소한의 정보 외의 개인정보 수집에 동의하지 아니한다는 이유로 정보주체에게 재화 또는 서비스의 제공을 거부하여서는 아니 된다.  <개정 2013. 8. 6.>

 제17조(개인정보의 제공)  ① 개인정보처리자는 다음 각 호의 어느 하나에 해당되는 경우에는 정보주체의 개인정보를 제3자에게 제공(공유를 포함한다. 이하 같다)할 수 있다.
  1. 정보주체의 동의를 받은 경우
  2. 제15조제1항제2호ㆍ제3호 및 제5호에 따라 개인정보를 수집한 목적 범위에서 개인정보를 제공하는 경우
  ② 개인정보처리자는 제1항제1호에 따른 동의를 받을 때에는 다음 각 호의 사항을 정보주체에게 알려야 한다. 다음 각 호의 어느 하나의 사항을 변경하는 경우에도 이를 알리고 동의를 받아야 한다.
  1. 개인정보를 제공받는 자
  2. 개인정보를 제공받는 자의 개인정보 이용 목적
  3. 제공하는 개인정보의 항목
  4. 개인정보를 제공받는 자의 개인정보 보유 및 이용 기간
  5. 동의를 거부할 권리가 있다는 사실 및 동의 거부에 따른 불이익이 있는 경우에는 그 불이익의 내용
  ③ 개인정보처리자가 개인정보를 국외의 제3자에게 제공할 때에는 제2항 각 호에 따른 사항을 정보주체에게 알리고 동의를 받아야 하며, 이 법을 위반하는 내용으로 개인정보의 국외 이전에 관한 계약을 체결하여서는 아니 된다.

 제21조(개인정보의 파기)  ① 개인정보처리자는 보유기간의 경과, 개인정보의 처리 목적 달성 등 그 개인정보가 불필요하게 되었을 때에는 지체 없이 그 개인정보를 파기하여야 한다. 다만, 다른 법령에 따라 보존하여야 하는 경우에는 그러하지 아니하다.
  ② 개인정보처리자가 제1항에 따라 개인정보를 파기할 때에는 복구 또는 재생되지 아니하도록 조치하여야 한다.
  ③ 개인정보처리자가 제1항 단서에 따라 개인정보를 파기하지 아니하고 보존하여야 하는 경우에는 해당 개인정보 또는 개인정보파일을 다른 개인정보와 분리하여서 저장ㆍ관리하여야 한다.
  ④ 개인정보의 파기방법 및 절차 등에 필요한 사항은 대통령령으로 정한다.

 제22조(동의를 받는 방법)  ① 개인정보처리자는 이 법에 따른 개인정보의 처리에 대하여 정보주체(제6항에 따른 법정대리인을 포함한다. 이하 이 조에서 같다)의 동의를 받을 때에는 각각의 동의 사항을 구분하여 정보주체가 이를 명확하게 인지할 수 있도록 알리고 각각 동의를 받아야 한다.  <개정 2017. 4. 18.>
  ② 개인정보처리자는 제1항의 동의를 서면(「전자문서 및 전자거래 기본법」 제2조제1호에 따른 전자문서를 포함한다)으로 받을 때에는 개인정보의 수집ㆍ이용 목적, 수집ㆍ이용하려는 개인정보의 항목 등 대통령령으로 정하는 중요한 내용을 행정안전부령으로 정하는 방법에 따라 명확히 표시하여 알아보기 쉽게 하여야 한다.  <신설 2017. 4. 18., 2017. 7. 26.>
  ③ 개인정보처리자는 제15조제1항제1호, 제17조제1항제1호, 제23조제1항제1호 및 제24조제1항제1호에 따라 개인정보의 처리에 대하여 정보주체의 동의를 받을 때에는 정보주체와의 계약 체결 등을 위하여 정보주체의 동의 없이 처리할 수 있는 개인정보와 정보주체의 동의가 필요한 개인정보를 구분하여야 한다. 이 경우 동의 없이 처리할 수 있는 개인정보라는 입증책임은 개인정보처리자가 부담한다.  <개정 2016. 3. 29., 2017. 4. 18.>
  ④ 개인정보처리자는 정보주체에게 재화나 서비스를 홍보하거나 판매를 권유하기 위하여 개인정보의 처리에 대한 동의를 받으려는 때에는 정보주체가 이를 명확하게 인지할 수 있도록 알리고 동의를 받아야 한다.  <개정 2017. 4. 18.>
  ⑤ 개인정보처리자는 정보주체가 제3항에 따라 선택적으로 동의할 수 있는 사항을 동의하지 아니하거나 제4항 및 제18조제2항제1호에 따른 동의를 하지 아니한다는 이유로 정보주체에게 재화 또는 서비스의 제공을 거부하여서는 아니 된다.  <개정 2017. 4. 18.>
  ⑥ 개인정보처리자는 만 14세 미만 아동의 개인정보를 처리하기 위하여 이 법에 따른 동의를 받아야 할 때에는 그 법정대리인의 동의를 받아야 한다. 이 경우 법정대리인의 동의를 받기 위하여 필요한 최소한의 정보는 법정대리인의 동의 없이 해당 아동으로부터 직접 수집할 수 있다.  <개정 2017. 4. 18.>
  ⑦ 제1항부터 제6항까지에서 규정한 사항 외에 정보주체의 동의를 받는 세부적인 방법 및 제6항에 따른 최소한의 정보의 내용에 관하여 필요한 사항은 개인정보의 수집매체 등을 고려하여 대통령령으로 정한다.  <개정 2017. 4. 18.>



<개인정보 수집․이용 제공 관련 사항>
1.	기본 개인정보 수집 • 이용 목적
1.1. 캠페인이 끝난 이후에도 컵이 반납되지 않을 시 연락을 취할 목적
1.2. 개인정보의 수집·이용 목적 중 재화나 서비스의 홍보 또는 판매 권유 등을 위하여 해당 개인정보를 이용하여 정보주체에게 연락할 수 있습니다.

2.	수집 • 이용하려는 개인정보 항목
2.1. 성명
2.2. 연락처
2.3. 이메일

3.	개인정보 이용 및 보유기간
3.1. 가입 시점으로부터 1년

4.	개인정보 제공 동의 거부 권리 및 동의 거부에 따른 제한 사항 
4.1. 위와 같이 개인정보를 수집 이용하는데 동의를 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 캠페인 관련 서비스 이용에 제한을 받을 수 있습니다.

※ 개인정보 제공자가 동의한 내용외의 다른 목적으로 활용하지 않으며, 제공된 개인정보의 이용을 거부하고자 할 때에는 개인정보 관리책임자를 통해 열람, 정정, 삭제를 요구할 수 있습니다.

본인은 본 「개인정보의 수집·이용·제3자 제공 동의서」내용을 읽고 명확히 이해하였으며, 이에 동의합니다.


위컵wecup
</textarea
          >
          <div class="policy-check-wrapper">
            <div class="policy-check">
              <input type="checkbox" v-model="formData.policyChk" name="" id="policy" />
              <label for="policy">위 개인정보 수집 · 이용에 동의합니다. (필수) </label>
            </div>
            <span class="error-msg">{{ errors.policyChk }}</span>
          </div>
        </div>

        <span class="error-msg" style="text-align:center;">{{ errors.response }}</span>
        <button @click.prevent="onSubmitForm">회원가입</button>
        <router-link :to="{ name: 'login' }" tag="span" class="caption"
          >계정이 이미 있어요</router-link
        >
      </form>
    </div>

    <div class="overlay-container overlay-left" v-if="role === 'partner'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>반가워요!</h1>
          <p>혹시, 컵을 빌리러 오셨나요?</p>
          <router-link
            :to="{ name: 'join', query: { role: 'customer' } }"
            tag="button"
            class="ghost"
          >
            사용자 회원가입하기</router-link
          >
        </div>
      </div>
    </div>

    <div class="overlay-container overlay-right" v-else-if="role === 'customer'">
      <div class="overlay">
        <div class="overlay-panel ">
          <h1>반가워요!</h1>
          <p>혹시, 제휴 카페 사장님이신가요?</p>
          <router-link :to="{ name: 'join', query: { role: 'partner' } }" tag="button" class="ghost"
            >파트너 회원가입하기</router-link
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faFacebookF, faGooglePlusG } from "@fortawesome/free-brands-svg-icons";
import { faComment, faMinus } from "@fortawesome/free-solid-svg-icons";
import { library as faLibrary } from "@fortawesome/fontawesome-svg-core";
import axios from "axios";
import router from "../routes/routes";

faLibrary.add(faFacebookF, faGooglePlusG, faComment, faMinus);

export default {
  router,
  data() {
    return {
      formData: {
        role: this.role,
        joinId: "",
        joinPw: "",
        joinPwChk: "",
        joinTel: {
          first: "",
          second: "",
          third: "",
        },
        joinAddress: {
          address: "",
          addressName: "",
          addressLat: "0",
          addressLng: "0",
        },
        policyChk: false,
      },
      errors: {
        joinId: "",
        joinPw: "",
        joinPwChk: "",
        joinTel: "",
        joinAddress: "",
        policyChk: "",
        response: "",
      },
      popup: null,
      loading: false,
      post: null,
      error: null,
      socialIconClicked: false,
    };
  },
  props: ["role"],
  components: { FontAwesomeIcon },
  methods: {
    goPopup() {
      const routeData = this.$router.resolve({ name: "address" });
      this.popup = window.open(
        routeData.href,
        "pop",
        "width=570, height=420,scrollbars=yes,resizable=yes",
      );
    },
    telInput() {
      event.target.nextElementSibling.nextElementSibling.children[0].focus();
    },
    nextInput() {
      event.target.nextElementSibling.nextElementSibling.focus();
    },
    nextPwInput() {
      event.target.nextElementSibling.nextElementSibling.nextElementSibling.focus();
    },
    nextTelInput() {
      event.target.nextElementSibling.nextElementSibling.focus();
    },
    limitNumber() {
      if ((event.target.name == "tel_first") & (event.target.value.length == 3)) {
        this.nextTelInput();
      }
      if ((event.target.name == "tel_first") & (event.target.value.length > 3)) {
        event.target.value = event.target.value.slice(0, 3);
        this.formData.joinTel.first = event.target.value;
      }
      if ((event.target.name == "tel_second") & (event.target.value.length == 4)) {
        this.nextTelInput();
      }
      if ((event.target.name == "tel_second") & (event.target.value.length > 4)) {
        event.target.value = event.target.value.slice(0, 4);
        this.formData.joinTel.second = event.target.value;
      }
      if ((event.target.name == "tel_third") & (event.target.value.length > 4)) {
        event.target.value = event.target.value.slice(0, 4);
        this.formData.joinTel.third = event.target.value;
      }
    },
    setValue() {
      this.formData.joinAddress.address = document.getElementById("location").value;
      this.formData.joinAddress.addressName = document.getElementById("location_name").value;
      this.formData.joinAddress.addressLat = document.getElementById("location_lat").value;
      this.formData.joinAddress.addressLng = document.getElementById("location_lng").value;

      console.log("this.formData.joinAddress :>> ", this.formData.joinAddress);
    },
    checkRequired() {
      for (const item in this.formData) {
        try {
          if (!this.formData[item]) {
            throw "빈 칸을 모두 채워주세요";
          } else {
            throw "";
          }
        } catch (error) {
          this.errors[item] = error;
        }
      }

      for (const item in this.formData.joinTel) {
        try {
          if (!this.formData.joinTel[item]) {
            throw "빈 칸을 모두 채워주세요";
          }
        } catch (error) {
          this.errors.joinTel = error;
        }
      }

      if (this.formData.role === "partner") {
        for (const item in this.formData.joinAddress) {
          try {
            if (!this.formData.joinAddress[item]) {
              throw "빈 칸을 모두 채워주세요";
            }
          } catch (error) {
            this.errors.joinAddress = error;
          }
        }
      }
    },
    checkId() {
      const pattern_joinId = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

      try {
        if (!pattern_joinId.test(this.formData.joinId)) {
          throw "이메일 형식을 확인해주세요";
        } else {
          this.errors.joinId = "";
        }
      } catch (error) {
        this.errors.joinId = error;
      }
    },
    checkPasswordForm() {
      const num = this.formData.joinPw.search(/[0-9]/g);
      const eng = this.formData.joinPw.search(/[a-z]/gi);
      const spe = this.formData.joinPw.search(/[~!@@#$%^&*]/gi);

      let cnt = 0;

      if (num == -1) cnt += 1;
      if (eng == -1) cnt += 1;
      if (spe == -1) cnt += 1;

      console.log("num :>> ", num);
      console.log("eng :>> ", eng);
      console.log("spe :>> ", spe);

      console.log("cnt :>> ", cnt);

      try {
        if (this.formData.joinPw.length < 8) {
          throw "8자리 이상으로 입력해주세요";
        } else if (this.formData.joinPw.search(/\s/) != -1) {
          throw "비밀번호는 공백없이 입력해주세요";
        } else if (cnt > 0) {
          throw "영어(대∙소문자), 숫자, 특수문자를 모두 혼합해주세요";
        } else if (this.formData.joinPw.search(/[()_+|<>?:{}/[\]/\\//"':;.,]/) != -1) {
          throw "정해진 특수문자 외 특수문자는 사용할 수 없습니다";
        } else {
          this.errors.joinPw = "";
        }
      } catch (error) {
        this.errors.joinPw = error;
      }
    },
    checkPasswordCheck() {
      try {
        if (this.formData.joinPw !== this.formData.joinPwChk) {
          throw "비밀번호가 위와 다릅니다";
        } else {
          this.errors.joinPwChk = "";
        }
      } catch (error) {
        this.errors.joinPwChk = error;
      }
    },
    checkCheckboxFilled() {
      try {
        console.log(this.formData.policyChk);
        if (!this.formData.policyChk) {
          throw "필수 동의 내용을 체크해주세요";
        } else {
          this.errors.policyChk = "";
        }
      } catch (error) {
        this.errors.policyChk = error;
      }
    },
    validate() {
      this.checkRequired();
      this.checkId();
      this.checkPasswordForm();
      this.checkPasswordCheck();
      this.checkCheckboxFilled();
    },
    async onSubmitForm(e) {
      e.preventDefault();
      this.validate();

      if (
        !this.errors.joinId &&
        !this.errors.joinPw &&
        !this.errors.joinPwChk &&
        !this.errors.joinTel &&
        !this.errors.joinAddress &&
        !this.errors.policyChk
      ) {
        // path
        const path = "/backend/join/" + this.formData.role;

        const payload = {
          email: this.formData.joinId,
          password: this.formData.joinPw,
          phoneNumber:
            this.formData.joinTel.first +
            this.formData.joinTel.second +
            this.formData.joinTel.third,
          address: this.formData.joinAddress.address,
          businessName: this.formData.joinAddress.addressName,
          latitude: this.formData.joinAddress.addressLat,
          longitude: this.formData.joinAddress.addressLng,
        };

        let sendJoinForm = axios.create();

        sendJoinForm.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
        sendJoinForm.defaults.headers.common["Content-Type"] = "application/json;charset=utf-8";

        await sendJoinForm
          .post(path, payload)
          .then((res) => {
            if (res.data.result == "fail") {
              throw res.data.message;
            } else {
              router.push({ name: "login", query: { role: this.formData.role } });
            }
          })
          .catch((error) => {
            console.log("error :>> ", error);
            this.errors.response = "이미 사용중인 이메일입니다.";
          });
      }
    },
    onClickSocialIcon() {
      this.socialIconClicked = true;
    },
    async openKakaoLogin() {
      const routeData = this.$router.resolve({ name: "kakao" });
      this.popup = window.open(
        routeData.href,
        "pop",
        "width=570, height=420,scrollbars=yes,resizable=yes",
      );
    },
  },
};
</script>

<style lang="scss" scoped>
@each $theme in $themes {
  &.#{map-get($theme, "name")} {
    background-color: map-get($map: $theme, $key: "background");
    color: map-get($map: $theme, $key: "text");
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    text-align: center;
    padding: auto 1rem;

    .container {
      background-color: map-get($map: $theme, $key: "content-background");
      border-radius: 10px;
      box-shadow: $shadow;
      position: relative;
      overflow: hidden;
      width: 1050px;
      max-width: 100%;
      min-height: 840px;
      margin: 1rem auto;

      .form-container {
        transition: all 0.6s ease-in-out;
        box-sizing: border-box;

        @media screen and (max-width: 1020px) {
          position: static;
        }

        .overlay-top {
          display: none;

          @media screen and (max-width: 1020px) {
            display: block;
            width: 100%;
            margin-top: 1rem;
            color: $main-color;
            font-size: 0.85rem;
            text-transform: uppercase;
            cursor: pointer;
            background-color: transparent;
            border: none;
            font-weight: bold;

            &:hover {
              text-decoration: underline;
            }
          }
        }

        form {
          background-color: map-get($map: $theme, $key: "content-background");
          display: flex;
          align-items: center;
          justify-content: center;
          flex-direction: column;
          padding: 2rem 3rem;
          text-align: center;
          box-sizing: border-box;

          h1 {
            font-weight: bold;
            margin: 0.5rem;
            font-size: 1.5rem;
          }

          .social-container {
            margin: 0.7rem 0;
            a {
              color: $main-color;
              border: 1px solid $main-color;
              border-radius: 50%;
              display: inline-flex;
              justify-content: center;
              align-items: center;
              margin: 0 0.1rem;
              height: 40px;
              width: 40px;
              transition: all 0.2s linear;

              &:hover {
                box-shadow: $shadow;
                background: $main-color;
                border: 1px solid white;
                color: white;
              }
            }

            .social-error {
              color: $error-msg;
              user-select: none;
              width: 100%;
              text-align: center;
              height: 1rem;
              font-size: 0.8rem;
              margin-bottom: 10px;
            }
          }

          span {
            font-size: 0.7rem;
            margin-bottom: 0.5rem;
          }

          .error-msg {
            color: $error-msg;
            user-select: none;
            width: 100%;
            text-align: left;
            padding-left: 1rem;
            height: 0.8rem;
          }

          .password-hint {
            color: map-get($map: $theme, $key: "text-light");
            user-select: none;
            height: 0.8rem;
          }

          input {
            background-color: map-get($map: $theme, $key: "background");
            color: map-get($map: $theme, $key: "text");
            border: none;
            padding: 1rem 1.4rem;
            margin: 0.3rem 0;
            width: 100%;
            box-sizing: border-box;

            &:focus {
              outline-color: $sub-color;
              border: none;
            }
          }

          .num-input-wrapper {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border: none;
            padding: 1rem 1.4rem;
            margin: 0.3rem 0;
            width: 100%;

            .num-input-connector {
              display: flex;
              justify-content: center;
              align-items: center;
              font-size: 0.6rem;
              margin: 3px;
              color: $main-color;
            }

            .num-input-title {
              user-select: none;
              padding: 0.5rem;
              font-size: 0.8rem;
            }

            .num-input {
              margin: 0 1rem;
              height: 43px;
              box-sizing: border-box;
              font-weight: 700;
              text-align: center;
              transition: border-color 0.2s ease-in-out;
              background-color: map-get($map: $theme, $key: "background");

              &:focus {
                outline-color: $sub-color;
                border: none;
                box-sizing: border-box;
              }

              &::placeholder {
                color: rgba(map-get($map: $theme, $key: "text-muted"), 0.5);
              }
            }
          }

          button {
            border-radius: 20px;
            margin-top: 1rem;
            border: 1px solid $main-color;
            background-color: $main-color;
            color: $white;
            border-radius: 20px;
            font-size: 1rem;
            font-weight: bold;
            padding: 1rem 3rem;
            text-transform: uppercase;
            cursor: pointer;

            &:active {
              transform: scale(0.95);
            }

            &:focus {
              outline: none;
            }

            &:hover {
              transform: scale(1.05);
              transition: all 0.2s ease-in-out;
            }
          }

          .address-input-wrapper {
            display: flex;
            width: 100%;

            input {
              cursor: pointer;
              border: none;
              padding: 1rem 1.4rem;
              width: 100%;
              margin-right: 1rem;
              box-sizing: border-box;
              background-color: map-get($map: $theme, $key: "content-blocked");
            }
            .address-btn {
              border-radius: 10px;
              cursor: pointer;
              font-weight: normal;
              padding: 0.5rem;
              box-sizing: border-box;
              white-space: nowrap;
              margin: 0.5rem;

              &:hover {
                transform: scale(1.05);
                transition: all 0.2s ease-in-out;
              }
            }
          }

          .address-detail-input-wrapper {
            display: flex;
            width: 100%;

            #location_num {
              width: 20%;
              margin-right: 0.5rem;
              padding: 0.5rem;
              box-sizing: border-box;
              text-align: center;
              cursor: default;
              user-select: none;
              -webkit-user-select: none;
              -ms-user-select: none;
              white-space: nowrap;
              background-color: map-get($map: $theme, $key: "content-blocked");
            }
          }

          .caption {
            margin: 1rem;
            text-decoration: underline;
            cursor: pointer;
            font-size: 0.75rem;
          }

          .policy-wrapper {
            display: flex;
            flex-direction: column;
            width: 100%;
            margin: 1rem 0;

            h1 {
              text-align: left;
              font-size: 1rem;
              padding-left: 1rem;
              color: map-get($map: $theme, $key: "text");
            }

            textarea {
              width: 100%;
              max-height: 100px;
              background-color: map-get($map: $theme, $key: "background");
              padding: 1rem;
              resize: none;
              box-sizing: border-box;
              border-radius: 6px;
              border: 1px solid map-get($map: $theme, $key: "border");
              color: map-get($map: $theme, $key: "text-light");

              &:focus {
                outline: none;
                border: 2px solid $main-color;
                color: map-get($map: $theme, $key: "text");
              }
            }

            .policy-check-wrapper {
              display: flex;
              flex-direction: column;
              justify-content: end;
              width: 100%;
              box-sizing: border-box;
              margin-top: 10px;

              .policy-check {
                width: 100%;
                display: flex;
                justify-content: flex-end;
                align-items: center;
                cursor: pointer;

                input {
                  margin-right: 5px;
                  width: fit-content;
                }
                label {
                  font-size: 0.8rem;
                  white-space: nowrap;
                  &:hover {
                    font-weight: bold;
                    cursor: pointer;
                  }
                }
              }

              .error-msg {
                text-align: right;
                box-sizing: border-box;
              }
            }
          }
        }
      }

      .partner-form-container {
        float: right;
        width: 50%;
        opacity: 1;
        z-index: 1;

        @media screen and (max-width: 1020px) {
          width: 100%;
        }
      }

      .customer-form-container {
        float: left;
        width: 50%;
        z-index: 2;

        @media screen and (max-width: 1020px) {
          width: 100%;
        }
      }

      .overlay-container {
        position: absolute;
        top: 0;
        width: 50%;
        height: 100%;
        overflow: hidden;
        transform: translateX(0);
        transition: transform 0.6s ease-in-out;

        .overlay {
          background-color: $main-color;
          color: $white;
          position: relative;
          height: 100%;
          width: 100%;
          transform: translateX(0);
          transition: transform 0.6s ease-in-out;

          .overlay-panel {
            position: absolute;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            padding: 0 3rem;
            text-align: center;
            top: 0;
            height: 100%;
            width: 100%;
            transform: translateX(0);
            transition: transform 0.6s ease-in-out;
            box-sizing: border-box;

            h1 {
              font-weight: bold;
              margin: 0.5rem;
              font-size: 1.5rem;
            }

            p {
              letter-spacing: 0.1rem;
              margin: 1.5rem 0 2rem;
              line-height: 1.2rem;
            }

            button {
              border-radius: 20px;
              border: 1px solid $white;
              background-color: transparent;
              color: $white;
              border-radius: 20px;
              font-weight: bold;
              padding: 1rem 3rem;
              letter-spacing: 0.05rem;
              cursor: pointer;

              &:active {
                transform: scale(0.95);
              }

              &:focus {
                outline: none;
              }

              &:hover {
                transition: all 0.2s ease-in-out;
                border: 1px solid $white;
                background-color: $white;
                color: $main-color;
              }
            }
          }
        }

        @media screen and (max-width: 1020px) {
          display: none;
        }
      }

      .overlay-left {
        left: 0;
      }

      .overlay-right {
        right: 0;
      }
    }
  }
}
</style>
