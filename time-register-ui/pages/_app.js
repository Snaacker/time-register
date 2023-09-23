import "antd/dist/antd.min.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "../src/App.css";
import "../src/index.css";
import AppWrapper from "./appWrapper";

export default function MyApp({ Component, pageProps }) {
  return (
    <AppWrapper>
      <div className="container-fluid">
        <Component {...pageProps} />
      </div>
    </AppWrapper>
  );
}