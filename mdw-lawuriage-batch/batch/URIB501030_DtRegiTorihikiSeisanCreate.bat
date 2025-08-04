REM @echo off
REM ============================================================================
REM タイトル：売上INVOICE作成処理
REM 説明    ：卸出荷伝票から売上INVOICEヘッダと明細を作成する。
REM version 1.00 2016/11/18 T.Chihara:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB501030
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
