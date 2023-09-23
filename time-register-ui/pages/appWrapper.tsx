import NavBar from "../src/components/nav/nav-bar";
import { createCtx } from "../src/contexts/AppContext";
import { User } from "../src/type/User";

const [ctx, UserProvider] = createCtx<User>({ id: "", role_name: "", user_name: "", isAuthenticated: false }, "stateUser", "updateUser");
export const UserContext = ctx;


export default function AppWrapper({ children }: any) {
    return (
        <UserProvider>
            <div>
                <main>{children}</main>
            </div>
        </UserProvider>
    );
}