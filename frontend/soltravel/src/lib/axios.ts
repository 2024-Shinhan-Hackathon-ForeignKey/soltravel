import axios from "axios"

// Axios 요청
const api = axios.create({
  baseURL: 'https://soltravel.shop/api/v1',
  // baseURL: 'http://3.107.138.21:8080/api/v1',
  // headers: {
  //   Authorization: `Bearer ${process.env.REACT_APP_ACCESS_TOKEN}`,
  // },
});

// 요청 인터셉터
api.interceptors.request.use(
  config => {
    // // accessToken을 sessionStorage에서 가져오고
    const accessToken = sessionStorage.getItem("accessToken");
    // // 토큰이 있으면 토큰을 넣어서 요청을 보냄
    // if (accessToken) {
    //   config.headers.Authorization = `${accessToken}`

    // 로그인 기능이 아직 없으므로 임시로 accessToken 지정 후 사용
    // const token = process.env.REACT_APP_ACCESS_TOKEN;
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config
  },
  error => Promise.reject(error),
);

// 응답 인터셉터
api.interceptors.response.use(
  response => response,
  error => Promise.reject(error),
);

export default api