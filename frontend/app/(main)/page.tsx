
import { CanHelp } from "@/components/home/CanHelp";
import { Hero } from "@/components/home/hero";
import { ProgramsResources } from "@/components/home/ProgramsResources";
import { Separator } from "@radix-ui/react-dropdown-menu";

export default function HomePage() {
    return (
        <main>
            <Hero />
            <CanHelp />
            <Separator />
            <ProgramsResources />
        </main>
    );
}