"use client";
import {
    Table,
    TableHeader,
    TableBody,
    TableHead,
    TableRow,
    TableCell,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { Edit, Trash2 } from "lucide-react";
import { useRouter } from "next/navigation";

type Evidence = {
    id: number;
    evidenceType: string;
    location: string;
    description: string;
    attachments?: string;
};
type InitialEvidenceTableProps = {
    data: any[];
    onEdit: (id: any) => void;
    onDelete: (id: number) => void;
    onAdd: () => void;
};

export default function InitialEvidenceTable({
    data,
    onDelete,
}: InitialEvidenceTableProps) {
    const router = useRouter();

    return (
        <div className="my-8">
            <div className="flex items-center mb-4">
                <div className="flex-1 border-t border-gray-300" />
                <h2 className="mx-4 font-semibold text-lg sm:text-2xl">
                    Initial Evidence
                </h2>
                <div className="flex-1 border-t border-gray-300" />
            </div>

            <div className="overflow-x-auto rounded-lg border border-gray-200 bg-white">
                <Table>
                    <TableHeader>
                        <TableRow className="bg-[#F8F8F8]">
                            <TableHead className="text-center font-semibold">ID</TableHead>
                            <TableHead className="text-center font-semibold">
                                Types of Evidence
                            </TableHead>
                            <TableHead className="text-center font-semibold">
                                Location
                            </TableHead>
                            <TableHead className="text-center font-semibold">
                                Description
                            </TableHead>
                            <TableHead className="text-center font-semibold">
                                Attachments
                            </TableHead>
                            <TableHead className="text-center font-semibold">Action</TableHead>
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
                            data.map((evidence) => (
                                <TableRow key={evidence.id} className="border-t border-gray-200">
                                    <TableCell className="text-center font-medium">
                                        #{evidence.id}
                                    </TableCell>
                                    <TableCell className="text-center">{evidence.evidenceType}</TableCell>
                                    <TableCell className="text-center">{evidence.location}</TableCell>
                                    <TableCell className="text-center max-w-xs truncate">
                                        {evidence.description}
                                    </TableCell>
                                    <TableCell className="text-center text-[#3B82F6]">
                                        {evidence.attachments || "—"}
                                    </TableCell>
                                    <TableCell className="text-center">
                                        <button
                                            className="inline-flex items-center mr-2 text-[#6C63FF] hover:text-blue-700"
                                            onClick={() =>
                                                router.push(`/reporter/initial?id=${evidence.id}`)
                                            }
                                        >
                                            <Edit size={18} />
                                        </button>
                                        <button
                                            className="inline-flex items-center text-[#F44336] hover:text-red-700"
                                            onClick={() => onDelete(evidence.id)}
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
                <Button
                    variant="outline"
                    className="bg-[#F3F6F9] text-[#434343] font-semibold rounded-md px-8"
                    onClick={() => router.push("/reporter/initial")}
                >
                    ADD
                </Button>
            </div>
        </div>
    );
}
