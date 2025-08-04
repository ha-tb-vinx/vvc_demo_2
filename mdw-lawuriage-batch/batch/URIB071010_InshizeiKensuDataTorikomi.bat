REM @echo off
REM ============================================================================
REM タイトル	：印紙税件数データ取込処理
REM 説明	：メモリDB上で計算された「印紙税件数データ」ファイルを元に、
REM 		  「店別印紙税対象ワーク」を更新する。
REM version 1.00 2009/06/04 ZH.ZHANG:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 印紙税件数データ取込処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB071010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1

