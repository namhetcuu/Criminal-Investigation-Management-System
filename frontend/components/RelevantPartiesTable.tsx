import {
    Table,
    TableHeader,
    TableBody,
    TableRow,
    TableHead,
    TableCell,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { Edit, Trash2 } from "lucide-react";

type Party = {
    id: number;
    relation?: string;
    fullname?: string;
    description?: string;
    attachments?: string;
};

type Props = {
    data: Party[];
    onEdit: (id: number) => void;
    onDelete: (id: number) => void;
    onAdd: () => void;
};

export default function RelevantPartiesTable({
    data = [],
    onEdit,
    onDelete,
    onAdd,
}: Props) {
    return (
        <div className="my-8">
            <div className="flex items-center mb-4">
                <div className="flex-1 border-t border-gray-300" />
                <h2 className="mx-4 font-semibold text-lg sm:text-2xl">Relevant Parties</h2>
                <div className="flex-1 border-t border-gray-300" />
            </div>
            <div className="overflow-x-auto border rounded-lg bg-white">
                <Table>
                    <TableHeader>
                        <TableRow className="bg-[#F8F8F8]">
                            {["ID", "Relevant Role", "Name", "Statement", "Attachments", "Action"].map((header) => (
                                <TableHead className="text-center font-semibold" key={header}>
                                    {header}
                                </TableHead>
                            ))}
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {data.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={6} className="text-center text-gray-400">
                                    No data
                                </TableCell>
                            </TableRow>
                        ) : (
                            data.map((party) => (
                                <TableRow key={party.id}>
                                    <TableCell className="text-center font-medium">#{party.id}</TableCell>
                                    <TableCell className="text-center">{party.relation || "—"}</TableCell>
                                    <TableCell className="text-center">{party.fullname || "—"}</TableCell>
                                    <TableCell className="text-center">{party.description || "—"}</TableCell>
                                    <TableCell className="text-center text-[#3B82F6]">{party.attachments || "—"}</TableCell>
                                    <TableCell className="text-center">
                                        <button
                                            className="mr-2 text-[#6C63FF]"
                                            onClick={() => onEdit(party.id)}
                                        >
                                            <Edit size={18} />
                                        </button>
                                        <button
                                            className="text-[#F44336]"
                                            onClick={() => onDelete(party.id)}
                                        >
                                            <Trash2 size={18} />
                                        </button>
                                    </TableCell>
                                </TableRow>
                            ))
                        )}
                    </TableBody>
                </Table>
            </div>
            <div className="flex justify-end mt-2">
                <Button variant="outline" className="bg-[#F3F6F9]" onClick={onAdd}>
                    ADD
                </Button>
            </div>
        </div>
    );
}
