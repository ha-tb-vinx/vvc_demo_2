REM @echo off
REM ============================================================================
REM タイトル：INVOICE発行／取消情報更新
REM 説明    ：Fレコード履歴カットデータ、卸出荷伝票VAT取消ワークより
REM 　　　　　売上INVOICE管理ヘッダーデータ更新
REM version 1.00 2017/08/08 N.Kato:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012407
IF %ERRORLEVEL% NEQ 0 goto error
:end

CD %BATCHF_HOME%
exit 0
:error
exit 1
